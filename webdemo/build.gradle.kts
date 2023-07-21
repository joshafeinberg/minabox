import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
}


kotlin {
    js(IR) {
        moduleName = "minabox"
        browser()
        binaries.executable()
    }

    wasm {
        moduleName = "minabox"
        browser {
            commonWebpackConfig {
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).copy(
                    static = (devServer?.static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(project.rootDir.path)
                        add(project.rootDir.path + "/shared/")
                        add(project.rootDir.path + "/nonAndroidMain/")
                        add(project.rootDir.path + "/webApp/")
                    },
                )
            }
        }
        binaries.executable()
    }

    sourceSets {
        val jsWasmMain by creating {
            dependencies {
                implementation(project(":demo"))
                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
            }
        }
        val jsMain by getting {
            dependsOn(jsWasmMain)
        }
        val wasmMain by getting {
            dependsOn(jsWasmMain)
        }
    }
}

compose.experimental {
    web.application {}
}
