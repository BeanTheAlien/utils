"use strict";
class Picker {
    clean(opts) {
        return { id: opts.id, startIn: opts.start };
    }
}
class FilePickerBase extends Picker {
    cleanOpts(opts) {
        return {
            ...this.clean(opts),
            excludeAcceptAllOption: opts.all,
            types: opts.accept?.map(a => { return { description: a.desc, accept: a.accept }; })
        };
    }
}
/**
 * Shows a file picker.
 *
 * [MDN reference](https://developer.mozilla.org/en-US/docs/Web/API/Window/showOpenFilePicker)
 * @since v1.0.7
 */
class FilePicker extends FilePickerBase {
    async pick(opts) {
        const [...handles] = await this.handle(opts);
        if (handles.length == 1) {
            const file = await handles[0].getFile();
            return await file.text();
        }
        const files = [];
        for (const handle of handles) {
            files.push(await handle.getFile());
        }
        const out = await Promise.all(files.map(async (o) => o.text()));
        return out;
    }
    async handle(opts) {
        const [...handles] = await window.showOpenFilePicker({ ...this.cleanOpts(opts), multiple: opts.mult });
        return handles;
    }
}
class SaveFilePicker extends FilePickerBase {
    async pick(opts) {
        return await this.handle(opts);
    }
    async handle(opts) {
        const handle = await window.showOpenSaveFilePicker({ ...this.cleanOpts(opts), suggestedName: opts.suggest });
        return handle;
    }
}
/**
 * Shows a directory picker.
 *
 * [MDN reference](https://developer.mozilla.org/en-US/docs/Web/API/Window/showDirectoryPicker)
 * @since v1.0.7
 */
class DirPicker extends Picker {
    async pick(opts) {
        const handle = await window.showDirectoryPicker(this.cleanOpts(opts));
        return handle;
    }
    async handle(opts) {
        return this.pick(opts);
    }
    cleanOpts(opts) {
        return {
            ...this.clean(opts),
            mode: opts.mode ? { "r": "read", "rw": "readwrite" }[opts.mode] : undefined
        };
    }
}
