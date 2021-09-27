public class private Unsafe() {}

    private static final Unsafe theUnsafe = new Unsafe();

    @CallerSensitive
    public static Unsafe getUnsafe() {
    Class cc = Reflection.getCallerClass();
    if (cc.getClassLoader() != null)
    throw new SecurityException("Unsafe");
    return theUnsafe;
    } UnsafeUrils {
        
        
    }
}
