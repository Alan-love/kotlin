// RUN_PIPELINE_TILL: FRONTEND
public fun bar(s: String) {
    System.out.println("Length of $s is ${s.length}")
}

public fun foo() {
    var s: Any = "not null"
    if (s is String) {
        s = 42
        bar(<!ARGUMENT_TYPE_MISMATCH!>s<!>)
    }
}
