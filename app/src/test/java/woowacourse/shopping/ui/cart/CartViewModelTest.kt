package woowacourse.shopping.ui.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import woowacourse.shopping.FakeCartRepository
import woowacourse.shopping.data.CartRepository
import woowacourse.shopping.di.Dependencies
import woowacourse.shopping.di.DependencyInjector
import woowacourse.shopping.di.DependencyInjector.inject
import woowacourse.shopping.model.Product

@RunWith(RobolectricTestRunner::class)
class CartViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `장바구니의 상품을 가져오면 Repository의 모든 상품을 반환한다`() {
        // given
        val expect = listOf(Product("", 0, ""))
        val cartRepository = FakeCartRepository(expect)

        DependencyInjector.dependencies = object : Dependencies {
            val cartRepository: CartRepository by lazy { cartRepository }
        }

        val viewModel = inject<CartViewModel>()

        // when
        viewModel.getAllCartProducts()

        // then
        val actual = viewModel.cartProducts.value
        assertEquals(expect, actual)
    }

    @Test
    fun `장바구니에 상품을 제거하면 상품 삭제 상태가 true가 되고 CartRepository에 상품을 제거한다`() {
        // given
        val products = listOf(Product("", 0, ""), Product("", 1, ""))
        val cartRepository = FakeCartRepository(products)

        DependencyInjector.dependencies = object : Dependencies {
            val cartRepository: CartRepository by lazy { cartRepository }
        }

        val viewModel = inject<CartViewModel>()

        // when
        viewModel.deleteCartProduct(0)

        // then
        val expect = listOf(Product("", 1, ""))
        assertEquals(true, viewModel.onCartProductDeleted.value)
        assertEquals(expect, cartRepository.products)
    }
}