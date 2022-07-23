package cn.micalhl.fireflyx.internal.compat

import net.milkbowl.vault.economy.AbstractEconomy
import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.economy.EconomyResponse
import org.bukkit.OfflinePlayer
import org.bukkit.plugin.ServicePriority
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.function.console
import taboolib.module.lang.sendLang
import cn.micalhl.fireflyx.api.FireflyXAPI
import cn.micalhl.fireflyx.api.FireflyXSettings
import cn.micalhl.fireflyx.util.plugin
import java.text.DecimalFormat
import cn.micalhl.fireflyx.module.impl.Money

@Suppress("deprecation")
class Vault : AbstractEconomy() {

    val decimalFormat = DecimalFormat().also {
        it.minimumFractionDigits = 2
        it.maximumFractionDigits = 2
    }

    override fun isEnabled(): Boolean = true

    override fun getName(): String = "FireflyX"

    override fun hasBankSupport(): Boolean = false

    override fun fractionalDigits(): Int = 2

    override fun format(amount: Double): String = "%.2f".format(amount)

    override fun currencyNamePlural(): String = currencyNameSingular()

    override fun currencyNameSingular(): String = FireflyXSettings.currencyName

    override fun hasAccount(name: String): Boolean =
        FireflyXAPI.databaseEconomy.has(plugin().server.getOfflinePlayer(name).uniqueId)

    override fun hasAccount(name: String, world: String): Boolean = false

    override fun getBalance(name: String): Double {
        if (!hasAccount(name)) {
            return 0.0
        }
        val user = plugin().server.getOfflinePlayer(name)
        return FireflyXAPI.databaseEconomy.get(user.uniqueId)
    }

    override fun getBalance(p0: String?, p1: String?): Double = 0.0

    override fun getBalance(player: OfflinePlayer): Double {
        return getBalance(player.name!!)
    }

    override fun has(p0: String, p1: Double): Boolean = getBalance(p0) >= p1

    override fun has(p0: String?, p1: String?, p2: Double): Boolean = false

    override fun withdrawPlayer(p0: String, p1: Double): EconomyResponse {
        val player = plugin().server.getOfflinePlayer(p0)
        if (!hasAccount(p0)) {
            return EconomyResponse(p1, getBalance(p0), EconomyResponse.ResponseType.FAILURE, null)
        }
        if (p1 < 0) {
            return EconomyResponse(p1, getBalance(p0), EconomyResponse.ResponseType.FAILURE, null)
        }
        cn.micalhl.fireflyx.api.FireflyXAPI.databaseEconomy.take(player.uniqueId, p1)
        return EconomyResponse(p1, getBalance(p0), EconomyResponse.ResponseType.SUCCESS, null)
    }

    override fun withdrawPlayer(p0: String, p1: String?, p2: Double): EconomyResponse {
        return EconomyResponse(p2, getBalance(p0), EconomyResponse.ResponseType.NOT_IMPLEMENTED, null)
    }

    override fun withdrawPlayer(player: OfflinePlayer, amount: Double): EconomyResponse {
        return withdrawPlayer(player.name!!, amount)
    }

    override fun depositPlayer(p0: String, p1: Double): EconomyResponse {
        val player = plugin().server.getOfflinePlayer(p0)
        if (!hasAccount(p0)) {
            return EconomyResponse(p1, getBalance(p0), EconomyResponse.ResponseType.FAILURE, null)
        }
        FireflyXAPI.databaseEconomy.add(player.uniqueId, p1)
        return EconomyResponse(p1, getBalance(p0), EconomyResponse.ResponseType.SUCCESS, null)
    }

    override fun depositPlayer(p0: String, p1: String?, p2: Double): EconomyResponse {
        return EconomyResponse(p2, getBalance(p0), EconomyResponse.ResponseType.NOT_IMPLEMENTED, null)
    }

    override fun depositPlayer(player: OfflinePlayer, amount: Double): EconomyResponse {
        return depositPlayer(player.name!!, amount)
    }

    override fun createBank(p0: String?, p1: String?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null)
    }

    override fun deleteBank(p0: String?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null)
    }

    override fun bankBalance(p0: String?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null)
    }

    override fun bankHas(p0: String?, p1: Double): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null)
    }

    override fun bankWithdraw(p0: String?, p1: Double): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null)
    }

    override fun bankDeposit(p0: String?, p1: Double): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null)
    }

    override fun isBankOwner(p0: String?, p1: String?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null)
    }

    override fun isBankMember(p0: String?, p1: String?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null)
    }

    override fun getBanks(): MutableList<String> = mutableListOf()

    override fun createPlayerAccount(p0: String): Boolean {
        val player = plugin().server.getOfflinePlayer(p0)
        FireflyXAPI.databaseEconomy.create(player.uniqueId)
        return true
    }

    override fun createPlayerAccount(p0: String?, p1: String?): Boolean = false

    override fun createPlayerAccount(player: OfflinePlayer): Boolean {
        return createPlayerAccount(player.name!!)
    }
}