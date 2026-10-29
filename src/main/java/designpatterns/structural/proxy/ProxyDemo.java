package designpatterns.structural.proxy;

import designpatterns.common.Console;

import java.util.List;

public class ProxyDemo {

    public static void main(String[] args) {
        Console.title("Proxy");

        Console.section("Virtual proxy (lazy loading)");
        List<Image> gallery = List.of(new LazyImageProxy("holiday.jpg"),
                new LazyImageProxy("family.jpg"), new LazyImageProxy("cat.jpg"));
        gallery.forEach(image -> Console.print("thumbnail", image.fileName()));
        Console.print("images loaded so far", HighResolutionImage.loadCount());
        Console.print("open one", gallery.get(1).display());
        Console.print("images loaded so far", HighResolutionImage.loadCount());

        Console.section("Protection proxy");
        ProtectedDocumentProxy viewer = new ProtectedDocumentProxy(new RealDocumentService(),
                ProtectedDocumentProxy.Role.VIEWER);
        Console.print("read", viewer.read("salary-report"));
        try {
            viewer.delete("salary-report");
        } catch (SecurityException e) {
            Console.print("delete", e.getMessage());
        }
        Console.print("audit log", viewer.auditLog());
    }
}
