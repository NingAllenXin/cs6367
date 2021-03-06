package org.pitest.mutationtest.engine.gregor.Mymutators;

import org.objectweb.asm.MethodVisitor; 
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.gregor.AbstractJumpMutator;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;

import java.util.HashMap;
import java.util.Map;

public enum ROR1 implements MethodMutatorFactory {

  ROR_MUTATOR;

  @Override
  public MethodVisitor create(final MutationContext context,
                              final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
    return new RORMethodVisitor(this, context, methodVisitor);
  }

  @Override
  public String getGloballyUniqueId() {
    return this.getClass().getName();
  }

  @Override
  public String getName() {
    return name();
  }

}

class RORMethodVisitor extends AbstractJumpMutator {

  private static final String                     DESCRIPTION = "Replace = with != (ROR)";
  private static final Map<Integer, Substitution> MUTATIONS   = new HashMap<Integer, Substitution>();

  static {
    // Compares a single value to 0
    MUTATIONS.put(Opcodes.IFEQ, new Substitution(Opcodes.IFNE, "Replace = with <> (ROR)"));
  
    MUTATIONS.put(Opcodes.IFNE, new Substitution(Opcodes.IFEQ, "Replace <> with = (ROR)"));
    
    MUTATIONS.put(Opcodes.IFLE, new Substitution(Opcodes.IFEQ, "Replace <= with = (ROR)"));
    
    MUTATIONS.put(Opcodes.IFGE, new Substitution(Opcodes.IFEQ, "Replace >= with = (ROR)"));
   
    MUTATIONS.put(Opcodes.IFGT, new Substitution(Opcodes.IFEQ, "Replace > with = (ROR)"));
    
    MUTATIONS.put(Opcodes.IFLT, new Substitution(Opcodes.IFEQ, "Replace < with = (ROR)"));
   

    // Compares two values
    MUTATIONS.put(Opcodes.IF_ICMPNE, new Substitution(Opcodes.IF_ICMPEQ, "Replace <> with = (ROR)"));
   
    MUTATIONS.put(Opcodes.IF_ICMPEQ, new Substitution(Opcodes.IF_ICMPNE, "Replace = with <> (ROR)"));
   
    MUTATIONS.put(Opcodes.IF_ICMPLE, new Substitution(Opcodes.IF_ICMPNE, "Replace < with <> (ROR)"));
    
    MUTATIONS.put(Opcodes.IF_ICMPGE, new Substitution(Opcodes.IF_ICMPNE, "Replace >= with <> (ROR)"));
    
    MUTATIONS.put(Opcodes.IF_ICMPGT, new Substitution(Opcodes.IF_ICMPNE, "Replace > with <> (ROR)"));
   
    MUTATIONS.put(Opcodes.IF_ICMPLT, new Substitution(Opcodes.IF_ICMPNE, "Replace < with <> (ROR)"));
    

    // Compares two object references and if they refer to the same object, then they are equal
    MUTATIONS.put(Opcodes.IF_ACMPEQ, new Substitution(Opcodes.IF_ACMPNE, DESCRIPTION));
    MUTATIONS.put(Opcodes.IF_ACMPNE, new Substitution(Opcodes.IF_ACMPEQ, DESCRIPTION));
  }

  RORMethodVisitor(final MethodMutatorFactory factory,
                   final MutationContext context, final MethodVisitor delegateMethodVisitor) {
    super(factory, context, delegateMethodVisitor);
  }

  @Override
  protected Map<Integer, Substitution> getMutations() {
    return MUTATIONS;
  }

}
