# Discrete Maths Proof Builder

A small Java library for building and pretty-printing natural deduction
proofs (propositional and predicate logic). It's used as a teaching aid
for the Discrete Mathematics course: instead of writing proofs on paper,
you build them as Java code, and the library checks each step and prints
the resulting proof tree.

## Getting started

You'll need:

- JDK 17 or later
- [Maven](https://maven.apache.org/)

Clone the repository and compile:

```bash
git clone https://github.com/ccol002/DiscreteMaths.git
cd DiscreteMaths
mvn compile
```

Run one of the bundled examples to check everything works:

```bash
mvn compile exec:java -Dexec.mainClass=discretemaths.examples.BasicPropositional
```

You should see several proofs printed to the console, each starting with a
line like `Proof Statement: ... |- ...`.

To use the library in your own class, either add another `main` class under
`src/main/java/discretemaths/examples/` and run it the same way, or import
`discretemaths.Proof` and `discretemaths.forms.Form` into your own Maven
project (`mvn install` first to put a jar in your local repository).

If you'd rather work in an IDE (Eclipse, IntelliJ, VS Code), just import the
project as an existing Maven project — the `pom.xml` is all it needs to set
up the classpath.

## Examples

The `examples` package contains runnable classes (each with a `main`
method) that double as worked examples of the API. A good order to read
them in:

| Class | Logic | What it shows |
|---|---|---|
| `BasicPropositional` | Propositional | The core rules: `andE1`, `andE2`, `impliesI`, `notI`/`notE`, `biimpliesI`, sub-proofs, and reusing an earlier proof with `lemma` |
| `AdvancedPropositional` | Propositional | A longer proof chaining several sub-proofs together (`or`/`not` reasoning) |
| `BasicPredicate` | Predicate | Quantifiers: `forallE` and `existsI` |
| `AdvancedPredicate` | Predicate | Nested quantifiers, `substAdd`/`substRem`, `forallI`, `existsE` |
| `Jan2016` | Predicate | A full past-exam-style proof combining quantifiers and propositional rules |

## Project structure

```
src/main/java/discretemaths/
├── Proof.java        # the proof builder — this is the main entry point
├── forms/             # the formula syntax tree
└── rules/             # one class per inference rule
└── examples/          # runnable worked examples (see table above)
```

### The `forms` package

`forms` defines the structure of formulae:

```
                                        Form
          ________________________________|______________________________________
         |                    |                    |                             |
        Atomic               Unary               Binary                      Quantifier
   ______|______              |            ________|_________             _______|_______
  |      |      |             |           |    |   |         |           |               |
True  False  Predicate       Not         And  Or  Implies  Biimplies    Forall         Exists
```

`Form` also has a number of static factory methods to make writing formulae
less verbose — see [Writing a proof](#writing-a-proof) below.

### The `rules` package

Rules have a flatter hierarchy and all extend `Rule`:

- **Structural rules (no logical checking):** `Hyp`, `SubHyp`, `Lemma`, `Copy`
- **Inference rules:** `AndE1`, `AndE2`, `AndI`, `BiimpliesE1`, `BiimpliesE2`,
  `BiimpliesI`, `ExistsE`, `ExistsI`, `ForallE`, `ForallI`, `NotE`, `NotI`,
  `OrE`, `OrI1`, `OrI2`, `ImpliesE`, `ImpliesI`
- **Variable substitution:** `SubstAdd`, `SubstRem`

You normally won't touch these classes directly — you call the
corresponding method on `Proof` (e.g. `.andI(...)`), and `Proof` applies
the rule for you.

### The `Proof` class

`Proof` brings everything together: you build a proof by chaining rule
methods, and it checks each step is valid as you go.

## Writing a proof

```java
import static discretemaths.Proof.begin;
import static discretemaths.forms.Form.$;
import static discretemaths.forms.Form.exists;

        begin()
/*1*/   .hyp(exists("x","X",exists("y", "Y", $("P"))))
/*2*/           .subhyp(exists("y", "Y", $("P")))
/*3*/                   .subhyp($("P"))
/*4*/                           .substAdd(3, "x")
/*5*/                           .existsI(4, "x", "X")
/*6*/                           .substAdd(5, "y")
/*7*/                           .existsI(6, "y", "Y")
                                .end()
/*8*/                   .impliesI(3, 7)
/*9*/                   .forallI(8, "y", "Y")
/*10*/                  .existsE(2, 9)
                        .end()
/*11*/          .impliesI(2, 10)
/*12*/          .forallI(11, "x", "X")
/*13*/          .existsE(1, 12)
        .end();
```

Each line number in the comments corresponds to a proof line — the numbers
aren't required by the compiler, they just help you keep track of which
call produces which line while you're writing the proof.

You can print the finished proof by calling `print()` or
`printStepped()`. `print()` shows the whole tree at once; the first call to
`printStepped()` shows the proof excluding sub-proofs, and each subsequent
call reveals the next level of sub-proofs, which is handy for walking
through a proof with students one level at a time.

Calling `print()` on the proof above yields:

```
Proof Statement: Ex:X.Ey:Y.P |- Ey:Y.Ex:X.P
   1: Ex:X.Ey:Y.P                   Hyp
     2: Ey:Y.P                        SubHyp
       3: P                             SubHyp
       4: (P)[x <- x]                   3, Law P = P[x <- x]
       5: Ex:X.P                        4, Exists Introduction
       6: (Ex:X.P)[y <- y]              5, Law P = P[x <- x]
       7: Ey:Y.Ex:X.P                   6, Exists Introduction
     8: (P => Ey:Y.Ex:X.P)            3-7, Implies Introduction
     9: Ay:Y.(P => Ey:Y.Ex:X.P)       8, Forall Introduction [y\1]
    10: Ey:Y.Ex:X.P                   2,9, Exists Elimination [y\Ey:Y.Ex:X.P]
  11: (Ey:Y.P => Ey:Y.Ex:X.P)       2-10, Implies Introduction
  12: Ax:X.(Ey:Y.P => Ey:Y.Ex:X.P)  11, Forall Introduction
  13: Ey:Y.Ex:X.P                   1,12, Exists Elimination [x\Ey:Y.Ex:X.P]
```

## License

See [LICENSE](LICENSE).
