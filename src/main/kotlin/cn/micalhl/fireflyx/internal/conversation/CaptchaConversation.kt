package cn.micalhl.fireflyx.internal.conversation

import org.bukkit.conversations.ConversationContext
import org.bukkit.conversations.Prompt
import org.bukkit.conversations.StringPrompt
import taboolib.common.platform.ProxyPlayer
import taboolib.module.lang.asLangText
import taboolib.module.lang.sendLang
import cn.micalhl.fireflyx.api.FireflyXSettings
import cn.micalhl.fireflyx.util.generate

class CaptchaConversation(private val user: ProxyPlayer, val func: () -> Unit) : StringPrompt() {

    private val captcha = generate(FireflyXSettings.captchaIndex)

    override fun getPromptText(context: ConversationContext): String {
        return user.asLangText("captcha", captcha)
    }

    override fun acceptInput(context: ConversationContext, input: String?): Prompt? {
        if (input == null || input.lowercase() == "cancel") {
            user.sendLang("captcha-cancel")
        } else if (input != captcha) {
            user.sendLang("captcha-different")
        } else {
            user.sendLang("captcha-success")
            func()
        }
        return END_OF_CONVERSATION
    }
}