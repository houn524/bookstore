/* ���� : Style.java					*
 * ����� : ����Ʈ��������μ���				*
 * ���� : Style�� enum Ŭ����				*/

public enum Style { 

  A, F;

  public String toString() {
    switch(this) {
      case A:   return "A style";
      case F:   return "F style";
      default:  return "Unspecified";
    }
  }
}