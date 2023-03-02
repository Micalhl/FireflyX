package cn.micalhl.fireflyx.core.inject

import cn.micalhl.fireflyx.core.FireflyX
import cn.micalhl.fireflyx.core.annotation.FireflyXModule
import cn.micalhl.fireflyx.core.annotation.ModuleEntry
import taboolib.common.LifeCycle
import taboolib.common.inject.ClassVisitor
import taboolib.common.platform.Awake
import taboolib.library.reflex.Reflex.Companion.invokeMethod
import java.lang.reflect.Method
import java.util.function.Supplier

/**
 * FireflyX
 * cn.micalhl.fireflyx.core.inject.AnnotationLoader
 *
 * @author mical
 * @since 2023/3/2 9:16 PM
 */
@Awake
object AnnotationLoader : ClassVisitor(0) {

    override fun getLifeCycle(): LifeCycle {
        return LifeCycle.ENABLE
    }

    override fun visitEnd(clazz: Class<*>, instance: Supplier<*>?) {
        if (clazz.isAnnotationPresent(FireflyXModule::class.java)) {
            val methods = clazz.methods.filter { it.isAnnotationPresent(ModuleEntry::class.java) }
            val map = hashMapOf<Method, Int>()
            for (method in methods) {
                val anno = method.getAnnotation(ModuleEntry::class.java)
                map[method] = anno.priority
            }
            map.toList().sortedBy { it.second }.forEach {
                try {
                    it.first.invoke(Any())
                    FireflyX.modules[clazz.simpleName] = clazz.invokeMethod<String>("name") ?: clazz.simpleName
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }
}