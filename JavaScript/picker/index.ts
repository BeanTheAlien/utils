/**
 * The options for pickers.
 * 
 * Includes `id` and `start` properties.
 */
interface PickerOptions {
    /**
     * By specifying an ID, the browser can remember different directories for different IDs.
     * 
     * If the same ID is used for another picker, the picker opens in the same directory.
     * 
     * As specified by MDN Web Docs.
     */
    id?: string;
    /**
     * A `FileSystemHandle` or a well known directory ("desktop", "documents", "downloads", "music", "pictures", or "videos") to open the dialog in.
     * 
     * As specified by MDN Web Docs.
     */
    start?: FileSystemStartPosition;
}
type WellKnownDir = "desktop" | "documents" | "downloads" | "music" | "pictures" | "videos";
type AcceptBase = { accept: { [x: string]: string[] } };
type Accepted = { desc?: string } & AcceptBase;
type OpenDirectoryAccessMode = "r" | "rw";
type OpenDirectoryFinalAccessMode = "read" | "readwrite";
type FileSystemStartPosition = FileSystemHandle | WellKnownDir;
type AcceptedFinal = { description?: string } & AcceptBase;
interface FilePickerBaseOptions extends PickerOptions {
    all?: boolean;
    accept?: Accepted[];
}
interface FilePickerBaseFinalOptions extends PickerCleanedOptions {
    excludeAcceptAllOption?: boolean;
    types?: AcceptedFinal[];
}
interface FilePickerOptions extends FilePickerBaseOptions {
    mult?: boolean;
}
interface FilePickerFinalOptions extends FilePickerBaseFinalOptions {
    multiple?: boolean;
}
interface FilePickerSaveOptions extends FilePickerBaseOptions {
    suggest?: string;
}
interface FilePickerSaveFinalOptions extends FilePickerBaseFinalOptions {
    suggestedName?: string;
}
interface DirPickerOptions extends PickerOptions {
    mode?: OpenDirectoryAccessMode;
}
interface DirPickerFinalOptions extends PickerCleanedOptions {
    mode?: OpenDirectoryFinalAccessMode;
}
type PickerCleanedOptions = { id?: string, startIn?: FileSystemStartPosition };
abstract class Picker<P, H> {
    /**
     * Generates a picker prompt and returns the result.
     * @param opts The picking options.
     */
    abstract pick(opts: PickerOptions): Promise<P>;
    /**
     * Returns the resulting item as chosen by the user during the prompt.
     * @param opts The picking options.
     */
    abstract handle(opts: PickerOptions): Promise<H>;
    /**
     * Generates a clean variant of the picking options.
     * @param opts The options to clean.
     */
    abstract cleanOpts(opts: PickerOptions): PickerOptions;
    /**
     * Generates a cleaned set of options given `id` and `start`.
     * @param opts The options to clean.
     * @returns The cleaned options.
     */
    clean(opts: PickerOptions): PickerCleanedOptions {
        return { id: opts.id, startIn: opts.start };
    }
}
abstract class FilePickerBase<T, H, R extends FilePickerBaseOptions, P extends FilePickerBaseFinalOptions> extends Picker<T, H> {
    cleanOpts(opts: R): P {
        return {
            ...this.clean(opts),
            excludeAcceptAllOption: opts.all,
            types: opts.accept?.map(a => { return { description: a.desc, accept: a.accept } })
        } as P;
    }
}
type FilePickerPickType = string | string[];
type FilePickerHandleType = FileSystemFileHandle[];
/**
 * Shows a file picker.
 * 
 * [MDN reference](https://developer.mozilla.org/en-US/docs/Web/API/Window/showOpenFilePicker)
 */
class FilePicker extends FilePickerBase<FilePickerPickType, FilePickerHandleType, FilePickerOptions, FilePickerFinalOptions> {
    async pick(opts: FilePickerOptions): Promise<FilePickerPickType> {
        const [...handles]: FilePickerHandleType = await this.handle(opts);
        if(handles.length == 1) {
            const file = await handles[0].getFile();
            return await file.text();
        }
        const files: File[] = [];
        for(const handle of handles) {
            files.push(await handle.getFile());
        }
        const out = await Promise.all(files.map(async o => o.text()));
        return out;
    }
    async handle(opts: FilePickerOptions): Promise<FilePickerHandleType> {
        const [...handles]: FilePickerHandleType = await (window as any).showOpenFilePicker({ ...this.cleanOpts(opts), multiple: opts.mult });
        return handles;
    }
}
class SaveFilePicker extends FilePickerBase<FileSystemHandle, FileSystemHandle, FilePickerSaveOptions, FilePickerSaveFinalOptions> {
    async pick(opts: FilePickerSaveOptions): Promise<FileSystemHandle> {
        return await this.handle(opts);
    }
    async handle(opts: FilePickerSaveOptions): Promise<FileSystemHandle> {
        const handle = await (window as any).showOpenSaveFilePicker({ ...this.cleanOpts(opts), suggestedName: opts.suggest });
        return handle;
    }
}
/**
 * Shows a directory picker.
 * 
 * [MDN reference](https://developer.mozilla.org/en-US/docs/Web/API/Window/showDirectoryPicker)
 */
class DirPicker extends Picker<FileSystemDirectoryHandle, FileSystemDirectoryHandle> {
    async pick(opts: DirPickerOptions): Promise<FileSystemDirectoryHandle> {
        const handle: FileSystemDirectoryHandle = await (window as any).showDirectoryPicker(this.cleanOpts(opts));
        return handle;
    }
    async handle(opts: DirPickerOptions): Promise<FileSystemDirectoryHandle> {
        return this.pick(opts);
    }
    cleanOpts(opts: DirPickerOptions): DirPickerFinalOptions {
        return {
            ...this.clean(opts),
            mode: opts.mode ? ({ "r": "read", "rw": "readwrite" } as const)[opts.mode] : undefined
        }
    }
}