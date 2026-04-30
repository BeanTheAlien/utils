public class Scene {
    public Array<Renderable> rend;
    public Scene(Renderable... objs) {
        this();
        this.rend.add(objs);
    }
    public Scene() {
        this.rend = new Array<Renderable>();
    }
}