// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// TARGET_BACKEND: JVM

// FILE: IntCollection.java
interface IntCollection<E> {
    public boolean add(int key);
}

// FILE: IntSet.java
interface IntSet extends IntCollection {
    public default boolean add(Integer it) { return true; }

    // from the supertype
    // public boolean add(int key);
}

// FILE: AbstractCollection.java
abstract class AbstractCollection<E> {
    public boolean add(E it) { return true; }
}

// FILE: AbstractIntCollection.java
abstract class AbstractIntCollection extends AbstractCollection<Integer> {
    public boolean add(int it) { return true; }

    // from the supertype
    // public default boolen add(Integer it) { return true; }
}

// FILE: AbstractIntSet.java
public abstract class AbstractIntSet extends AbstractIntCollection implements IntSet {}

// FILE: Main.kt

class KotlinClass : AbstractIntSet()

/* GENERATED_FIR_TAGS: classDeclaration, javaType */
