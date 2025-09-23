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

# Chi sono

- <div style="display:flex; align-items:center; gap:12px; justify-content: space-between">
    <span>Full Stack Software Engineer @ Kuama</span>
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
- Appassionato di developer experience e clean code

---

# Perché parlare di DSL?

- Configurazioni o test **verbosi e ripetitivi**
- Codice difficile da leggere e mantenere
- Una DSL rende il codice **più vicino al linguaggio del dominio**

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

# Agenda

1. Cos’è una DSL
2. Strumenti Kotlin (builder pattern, lambda con receiver)
3. Caso pratico: mini framework di test
    - `expect` + infix matchers
    - `should`
    - `test`
    - `@DslMarker`
4. Confronto con codice imperativo
5. Conclusioni
6. Q&A

---

# Cos’è una DSL?

- Domain Specific Language = codice che sembra un linguaggio dedicato
- Esempi in Kotlin: apply { ... }, buildString { ... }, Compose
- Obiettivo: rendere il codice descrittivo e chiaro

---

# Strumento 1: Builder pattern

```kotlin
fun config(block: ConfigBuilder.() -> Unit) =
    ConfigBuilder().apply(block).build()

```

Permette di costruire API fluide e leggibili

---

# Strumento 2: Lambda con receiver

```kotlin
config {
    db { url = "postgres://..." }
    server { port = 8080 }
}
```

Lo scope diventa l'oggetto su cui lavori

---

# Il problema: test imperativi

```kotlin
val result = 2 + 2
if (result != 4) error("Expected 4")
```

- Ripetitivo
- Messaggi poco chiari
- Nessuna struttura

---

# DSL: il risultato che vogliamo

```kotlin
suite("Calculator Suite") {
    test("Basic math") {
        expect("it adds numbers") {
            2 + 2.shouldBe(4)
        }
    }
}

```

Leggibile, espressivo, sicuro ✌️

---

# Step 1: `Assertions`

```kotlin
expect(2 + 2).toBe(4)
```

- Teoria: incapsulare l'assert
- Live coding

<!--
fun <T> expect(actual: T): T = actual

fun <T> T.toBe(expected: T): AssertionResult =
    if (expected == this) ok("OK: $expected == $this") else fail(
        "FAIL: $expected != $this"
    )

fun main() {
    println(expect(2 + 2).toBe(4).message)
    println(expect(2 + 2).toBe(5).message)
}
-->

---

# Step 2: `expect`

```kotlin
expect("it add numbers") {
    2 + 2 shouldBe 4
}
```

- Teoria: grouping e messaggi descrittivi
- Live coding: `Assertion` + `TestBuilder.assert()`

---

# Step 3: `test`

```kotlin

test("Calculator") {
    should("adds numbers") { 2 + 2.shouldBe(4) }
    should("divides with remainder") { 7 % 3.shouldBe(1) }
}

```

- Teoria: suite di test
- Live coding: `Test`, `TestSuite`, `TestSuiteBuilder`, `test { }`

---

# Problema di scope

```kotlin
test("Calculator") {
    should("outer") {
        should("inner") { 1 + 1 shouldBe 2 } // allowed 😱
    }
}
```

- Ambiguità: quale `should`?

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

- Eseguiamo il codice in /end

---

# Confronto

### Imperativo

```kotlin
if (2 + 2 != 4) error("Expected 4")
```

### DSL

```kotlin
test("Calculator") {
    should("adds numbers") { 
        2 + 2 shouldBe 4 
    }
}

```

---

# Conclusioni

- Espressività
- Sicurezza
- Manutebilità
- Estendibilità (matcher personalizzati, before/after...)

---

# Q&A

### Domande?

- 📂 Repo su GitHub con esempio completo https://github.com/matteovalerio/kotlin-unit-test-dsl
- 📱 LinkedIn: linkedin.com/in/matteo-valerio-9336611b9/
