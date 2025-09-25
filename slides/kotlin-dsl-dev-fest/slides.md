---
theme: seriph
colorSchema: light
highlighter: shiki
shikiTheme: github-light
lineNumbers: true
fonts:
  code: "Fira Code"
css: ./style.css
layout: cover
class: cover-diag
background: "linear-gradient(135deg, #7f52ff 0%, #c792ea 50%, #ff9800 100%)"
---

# Costruire DSL in Kotlin

### Matteo Valerio

Full-Stack Developer @ Kuama • Author @ Hyperskill (JetBrains Academy)  
DevFest Venezia 2025

---

# Chi sono - Matteo Valerio

- <div style="display:flex; align-items:center; gap:12px; justify-content: space-between">
    <span>Full Stack Developer @ Kuama</span>
    <div style="display: flex; align-items: center"><img src="/kuama.svg" alt="Kuama logo" style="height:16px"/>
  </div>
  </div>
- <div style="display:flex; align-items:center; gap:12px; justify-content: space-between">
    <span>Author @ Hyperskill (JetBrains Academy)</span>
  <div style="display:flex; align-items:center; gap:4px">
    <img src="/hyperskill-logo.svg" alt="Hyperskill logo" style="height:16px"/>
    <img src="/hyperskill-title.svg" alt="Hyperskill logo" style="height:16px"/>
  </div>
  </div>
- Esperienza su web, mobile, desktop
- Appassionato di developer experience

---

# Agenda

1. Perché parlare di DSL? 
2. Cos’è una DSL
3. Strumenti Kotlin (builder pattern, lambda con receiver)
4. Caso pratico: mini framework di test
    - `expect` + infix matchers
    - `should`
    - `test`
    - `@DslMarker`
5. Confronto con codice imperativo
6. Conclusioni
7. Q&A

---

# Perché parlare di DSL?

- Configurazioni o codice **verboso e ripetitivo**
- Codice difficile da leggere e mantenere
- Una DSL rende il codice **più vicino al linguaggio del dominio**

---

# Cos’è una DSL?

- Domain Specific Language = codice che sembra un linguaggio dedicato
- Obiettivo: rendere il codice descrittivo e chiaro
- Internal vs External DSL

---

# Esempio: Ktor
```kotlin
routing {
    get("/") {
        val name = "Ktor"
        call.respondHtml(HttpStatusCode.OK) {
            head {
                title {
                    +name
                }
            }
            body {
                h1 {
                    +"Hello from $name!"
                }
            }
        }
    }
}
```

---

# Esempio: TeamCity configuration

```kotlin
object Build : BuildType({
    name = "${DslContext.getParameter("BuildName", "Test Build")}"

    script {
        scriptContent = "echo Build Successful"
    }

    script {
        scriptContent = "echo Deploy"
        enabled = DslContext.getParameter(name = "Environment") != "Staging"
    }

})
```

---

# Esempio: Gradle .kts

```kotlin
plugins {
    `java-library`
}

dependencies {
    api("junit:junit:4.13")
    implementation("junit:junit:4.13")
    testImplementation("junit:junit:4.13")
}

tasks {
    test {
        testLogging.showExceptions = true
        useJUnit()
    }
}
```

---

# Strumento 1: Builder pattern

```kotlin
ConfigBuilder().apply(block).build()

```

Permette di costruire API fluide e leggibili

<img src="./lego-builder.jpg" height="200" width="400" />

---

# Strumento 2: Lambda con receiver

<span v-click-hide>
```kotlin
fun config(myConfig: (builder: ConfigBuilder) -> Unit)
```
</span v-click-hide>
<span v-click>
```kotlin
fun config(myConfig: ConfigBuilder.() -> Unit)

config {
    db { url = "postgres://..." }
    server { port = 8080 }
}
```
</span v-click>

Lo scope diventa l'oggetto su cui lavori

---

# Il problema: test imperativi

```kotlin
println("Calculator suite")
println("Test basic math")
val result = 2 + 2
if (result != 4) throw Error("Expected 4")
```

<ul>
    <li>Ripetitivo</li>
    <v-click>
    <li>
        Messaggi poco chiari
    </li>
    </v-click>
    <v-click>
        <li>
        Nessuna struttura
        </li>
    </v-click>
</ul>

---

# DSL: il risultato che vogliamo

```kotlin
suite("Calculator Suite") {
    test("Basic math") {
        expect("it adds numbers") {
            2 + 2 shouldBe 4
        }
    }
}

```

Leggibile, espressivo, sicuro ✌️

---

# Step 1: `Assertions` e `expect` 

```kotlin
expect("it adds two numbers") {
    (2 + 2).shouldBe(4)
}
```

- Incapsulare l'assert
- Live coding

<!--
fun <T> expect(actual: T): T = actual

fun <T> T.shouldBe(expected: T): AssertionResult =
    if (expected == this) ok("OK: $expected == $this") else fail(
        "FAIL: $expected != $this"
    )

fun main() {
    println(expect(2 + 2).shouldBe(4).message)
    println(expect(2 + 2).shouldBe(5).message)
}
-->

---

# Step 2: `test`

```kotlin
test("it adds two numbers") {
    expect("it adds two numbers") {
        (2 + 2).shouldBe(4)
    }
}
```

- Teoria: grouping e messaggi descrittivi
- Live coding: `Assertion` + `TestBuilder.assert()`

---

# Step 3: `suite`

```kotlin

suite("Calculator") {
    test("it adds two numbers") {
        expect("it adds two numbers") {
            (2 + 2).shouldBe(4)
        }
    }
    test("divides with remainder") {
        expect("7 % 3 has 1 remainder") {
            7 % 3.shouldBe(1)
        }
    }
}

```

- Teoria: suite di test
- Live coding: `Test`, `TestSuite`, `TestSuiteBuilder`, `test { }`

---

# Problema di scope

```kotlin
test("Calculator") {
    expect("outer") {
        expect("inner") { 1 + 1 shouldBe 2 } // allowed 😱
    }
}
```

- Ambiguità: quale `expect`?

---

# Soluzione: `@DslMarker`

```kotlin
@DslMarker
annotation class TestDsl

@TestDsl
class TestSuiteBuilder { ... }

@TestDsl
class TestBuilder { ... }
```

Ora il compilatore impedisce errori di scope ✅

---

# Wrap-up

- Vediamo la versione completa in /end”

---

# Confronto

### Imperativo
<v-click>

```kotlin
println("Refactor Suite")
println("After renaming variable")
val result = refactor("foo" to "bar")
println("nothing should break")
if (result.buildStatus != "✅ green") throw Error("Refactoring == Breaking")
```
</v-click>

### DSL
<span v-click>

```kotlin
suite("Refactor Suite") {
    test("After renaming variable") {
        expect("nothing should break") {
            refactor("foo" to "bar").buildStatus shouldBe "✅ green"
        }
    }
}

```
</span v-click>
---

# Conclusioni

- Espressività
- Sicurezza
- Manutenibilità
- Possibili estensioni (matcher personalizzati, before/after...)

---

# Q&A

```kotlin
while(true) {
    audience.ask()
    me.tryAnswer()
}
```

<div style="text-align:center; margin-top:1em; font-size:3rem">
  ❓💬
</div>

---

# Contatti

<div style="display:flex; justify-content:space-around; margin-top:2em">

<div style="text-align:center">
  <img src="/qr-code-github.png" style="height:180px"/>
  <div style="margin-top:10px; color:#7f52ff; font-weight:600">GitHub</div>
</div>

<div style="text-align:center">
  <img src="/qr-code-linkedin.png" style="height:180px"/>
  <div style="margin-top:10px; color:#ff9800; font-weight:600">LinkedIn</div>
</div>

</div>

---

# Thank you!

```kotlin 
fun main() = println("Thanks DevFest Venezia ❤️")
```



