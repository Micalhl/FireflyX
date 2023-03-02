@file:Suppress("PropertyName", "SpellCheckingInspection")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val taboolib_version: String by project

val kotlinVersionNum: String
    get() = project.kotlin.coreLibrariesVersion.replace(".", "")

plugins {
    `maven-publish`
    java
    id("org.jetbrains.kotlin.jvm") version "1.5.31"
    id("com.github.johnrengelman.shadow") version "7.1.2" apply false
}

subprojects {
    apply<JavaPlugin>()
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")
    apply(plugin = "com.github.johnrengelman.shadow")
    repositories {
        mavenLocal()
        mavenCentral()
        maven {
            url = uri("http://ptms.ink:8081/repository/releases/")
            isAllowInsecureProtocol = true
        }
    }

    dependencies {
        compileOnly(kotlin("stdlib"))
        // 引入 Taboolib
        // 注意 common 模块不可使用 implementation 引入（即使用 ShadowJar 插件打包）
        // 因为需要借助 TabooLib Gradle 插件修改一些东西
        compileOnly("io.izzel.taboolib:common:$taboolib_version")
        // 使用 shadowJar 插件打包的模块
        implementation("io.izzel.taboolib:common-5:$taboolib_version")
        implementation("io.izzel.taboolib:module-chat:$taboolib_version")
        implementation("io.izzel.taboolib:module-configuration:$taboolib_version")
        implementation("io.izzel.taboolib:platform-bukkit:$taboolib_version")
        implementation("io.izzel.taboolib:expansion-command-helper:$taboolib_version")
        // 如果你要加新的 TabooLib 模块，就写到下面
        implementation("io.izzel.taboolib:module-lang:$taboolib_version")
    }


    // =============================
    //       下面的东西不用动
    // =============================
    java {
        withSourcesJar()
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjvm-default=all", "-Xextended-compiler-checks")
        }
    }
    // 这里不要乱改
    tasks.withType<ShadowJar> {
        // 重定向 TabooLib
        relocate("taboolib", "${rootProject.group}.taboolib")
        // 重定向 Kotlin
        relocate("kotlin.", "kotlin${kotlinVersionNum}.") { exclude("kotlin.Metadata") }

        // =============================
        //     如果你要重定向就在下面加
        // =============================
        // relocate("org.spongepowered.math", "${rootProject.group}.library.math")
    }
}

gradle.buildFinished {
    buildDir.deleteRecursively()
}

subprojects
        .filter { it.name != "project" && it.name != "plugin" }
        .forEach { proj ->
            proj.publishing { applyToSub(proj) }
        }

fun PublishingExtension.applyToSub(subProject: Project) {
    repositories {
        maven("http://repo.mcstarrysky.com:10086/repository/releases") {
            isAllowInsecureProtocol = true
            credentials {
                username = System.getProperty("publishingUser")
                password = System.getProperty("publishingPwd")
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
        mavenLocal()
    }
    publications {
        create<MavenPublication>("maven") {
            artifactId = subProject.name
            groupId = project.group.toString()
            version = project.version.toString()
            artifact(subProject.tasks["kotlinSourcesJar"])
            artifact(subProject.tasks["shadowJar"]) {
                classifier = null
            }
            println("> Apply \"$groupId:$artifactId:$version\"")
        }
    }
}