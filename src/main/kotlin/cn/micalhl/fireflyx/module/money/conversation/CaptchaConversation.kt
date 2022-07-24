package cn.micalhl.fireflyx.module.money.conversation

import org.bukkit.conversations.ConversationContext
import org.bukkit.conversations.Prompt
import org.bukkit.conversations.StringPrompt
import taboolib.common.platform.ProxyPlayer
import taboolib.module.lang.asLangText
import taboolib.module.lang.sendLang
import cn.micalhl.fireflyx.common.config.Settings
import cn.micalhl.fireflyx.module.money.generate

class CaptchaConversation(private val user: ProxyPlayer, val func: () -> Unit) : StringPrompt() {

    private val captcha = generate(Settings.captchaIndex)

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