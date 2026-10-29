package designpatterns.structural;

import designpatterns.structural.proxy.HighResolutionImage;
import designpatterns.structural.proxy.LazyImageProxy;
import designpatterns.structural.proxy.ProtectedDocumentProxy;
import designpatterns.structural.proxy.ProtectedDocumentProxy.Role;
import designpatterns.structural.proxy.RealDocumentService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProxyTest {

    @Test
    void virtualProxyLoadsOnlyOnFirstUse() {
        int before = HighResolutionImage.loadCount();
        LazyImageProxy proxy = new LazyImageProxy("x.png");
        proxy.fileName();
        assertEquals(before, HighResolutionImage.loadCount());
        proxy.display();
        proxy.display();
        assertEquals(before + 1, HighResolutionImage.loadCount());
    }

    @Test
    void protectionProxyChecksRole() {
        var viewer = new ProtectedDocumentProxy(new RealDocumentService(), Role.VIEWER);
        var admin = new ProtectedDocumentProxy(new RealDocumentService(), Role.ADMIN);
        assertThrows(SecurityException.class, () -> viewer.delete("salary-report"));
        assertDoesNotThrow(() -> admin.delete("salary-report"));
        assertEquals("<not found>", admin.read("salary-report"));
    }
}
