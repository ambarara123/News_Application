package com.example.testapp.ui.billing


import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.testapp.R
import com.example.testapp.databinding.FragmentBillingBinding
import com.example.testapp.ui.base.BaseFragment
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.WalletConstants

/**
 * A simple [Fragment] subclass.
 */
class BillingFragment : BaseFragment<FragmentBillingBinding, BillingViewModel>() {

    override fun getViewModelClass(): Class<BillingViewModel> = BillingViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_billing

    private lateinit var paymentsClient: PaymentsClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        paymentsClient = createPaymentClient(context!!)
    }

    private fun createPaymentClient(context: Context): PaymentsClient {
        val walletOptions = Wallet.WalletOptions.Builder()
            .setEnvironment(WalletConstants.ENVIRONMENT_TEST).build()

        return Wallet.getPaymentsClient(context, walletOptions)
    }

}
