package designpatterns.spring.chainofresponsibility;

public record OrderRequest(String customer, int quantity, String couponCode) {
}
