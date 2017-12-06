/* ���� : Type.java					*
 * ����� : ����Ʈ��������μ���				*
 * ���� : Type�� enum Ŭ����				*/

public enum GuitarType { 

  ACOUSTIC, ELECTRIC;

  public String toString() {
    switch(this) {
      case ACOUSTIC: return "Acoustic";
      case ELECTRIC: return "Electric";
      default:       return "Unspecified";
    }
  }
}