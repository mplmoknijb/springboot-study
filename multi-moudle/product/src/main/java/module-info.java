module product {
    requires order;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    exports cn.leon.product;
}