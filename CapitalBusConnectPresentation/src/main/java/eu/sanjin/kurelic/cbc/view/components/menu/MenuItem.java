package eu.sanjin.kurelic.cbc.view.components.menu;

public class MenuItem {

    private String name;
    private String onClick;
    private String extraClass;

    public MenuItem(String name, String onClick) {
        this.name = name;
        this.onClick = onClick;
        this.extraClass = "";
    }

    public MenuItem(String name, String onClick, String... extraClasses) {
        this.name = name;
        this.onClick = onClick;
        buildClasses(extraClasses);
    }

    private void buildClasses(String... extraClasses) {
        StringBuilder sb = new StringBuilder();
        String space = "";
        for(String extraClass : extraClasses) {
            sb.append(space).append(extraClass);
            space = " ";
        }
        this.extraClass = sb.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOnClick() {
        return onClick;
    }

    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    public String getExtraClass() {
        return extraClass;
    }

    public void setExtraClass(String extraClass) {
        this.extraClass = extraClass;
    }
}
