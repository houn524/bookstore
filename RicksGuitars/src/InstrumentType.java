/* 파일 : InstrumentType.java				*
 * 과목명 : 소프트웨어개발프로세스					*
 * 서술 : InstrumentType의 enum 클래스		*/

public enum InstrumentType { 

  GUITAR, BANJO, DOBRO, FIDDLE, BASS, MANDOLIN;

  public String toString() {
    switch(this) {
      case GUITAR:   return "Guitar";
      case BANJO:    return "Banjo";
      case DOBRO:    return "Dobro";
      case FIDDLE:   return "Fiddle";
      case BASS:     return "Bass";
      case MANDOLIN: return "Mandolin";
      default:       return "Unspecified";
    }
  }
}