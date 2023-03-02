package cn.micalhl.fireflyx.core.annotation

/**
 * FireflyX
 * cn.micalhl.fireflyx.core.annotation.ModuleEntry
 *
 * @author mical
 * @since 2023/3/2 9:15 PM
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class ModuleEntry(val priority: Int = 0)
