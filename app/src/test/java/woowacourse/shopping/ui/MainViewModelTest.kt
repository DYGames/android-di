package woowacourse.shopping.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import woowacourse.shopping.FakeCartRepository
import woowacourse.shopping.FakeProductRepository
import woowacourse.shopping.data.CartRepository
import woowacourse.shopping.data.DefaultProductRepository
import woowacourse.shopping.data.ProductRepository
import woowacourse.shopping.di.Dependencies
import woowacourse.shopping.di.DependencyInjector
import woowacourse.shopping.di.DependencyInjector.inject
import woowacourse.shopping.model.Product

@RunWith(RobolectricTestRunner::class)
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `장바구니에 상품을 등록하면 상품 등록 상태가 true가 되고 CartRepository에 Product가 추가된다`() {
        // given
        val cartRepository = FakeCartRepository()

        DependencyInjector.dependencies = object : Dependencies {
            val cartRepository: CartRepository by lazy { cartRepository }
            val productRepository: ProductRepository by lazy { DefaultProductRepository() }
        }

        val viewModel = inject<MainViewModel>()

        // when
        val expect = Product("", 0, "")
        viewModel.addCartProduct(expect)

        // then
        assertEquals(true, viewModel.onProductAdded.value)
        assertEquals(expect, cartRepository.products[0])
    }

    @Test
    fun `장바구니의 상품을 가져오면 CartRepository의 모든 상품을 가져온다`() {
        // given
        val expect = listOf(Product("", 0, ""))
        val cartRepository = FakeCartRepository()
        val productRepository = FakeProductRepository(expect)

        DependencyInjector.dependencies = object : Dependencies {
            val cartRepository: CartRepository by lazy { cartRepository }
            val productRepository: ProductRepository by lazy { productRepository }
        }

        val viewModel = inject<MainViewModel>()

        // when
        viewModel.getAllProducts()

        // then
        val actual = viewModel.products.value
        assertEquals(expect, actual)
    }
}