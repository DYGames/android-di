package woowacourse.shopping.data

import woowacourse.shopping.model.Product

// TODO: Step2 - CartProductDao를 참조하도록 변경
class DefaultCartRepository(cartProducts: List<Product> = emptyList()) :
    CartRepository {
    private val cartProducts: MutableList<Product> = cartProducts.toMutableList()

    override fun addCartProduct(product: Product) {
        cartProducts.add(product)
    }

    override fun getAllCartProducts(): List<Product> {
        return cartProducts.toList()
    }

    override fun deleteCartProduct(id: Int) {
        cartProducts.removeAt(id)
    }
}
