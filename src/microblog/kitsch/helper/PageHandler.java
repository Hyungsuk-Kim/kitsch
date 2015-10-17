package microblog.kitsch.helper;

public class PageHandler {
   // 한 페이지에 보여줄 게시글 개수
   public static final int PAGE_LIST_SIZE = 10;
   // 페이지 선택 바에 보여줄 페이지 개수
   public static final int PAGE_GROUP_SIZE = 3;
   
   public static int getTotalPageCount(int totalBoardCount){
      return (int)Math.ceil(totalBoardCount/(float)PAGE_LIST_SIZE);
   }
   //특정 페이지의 페이지 선택 바에 표시 될 시작 페이지 번호를 구한다.
   public static int getStartPageNumber(int pageNumber){
      return (pageNumber - 1) / PAGE_GROUP_SIZE * PAGE_GROUP_SIZE+1;
   }
   //특정 페이지의 페이지 선택 바에 표시 될 끝 페이지 번호를 구한다.
   public static int getEndPageNumber(int pageNumber, int totalBoardCount){
      int endPageNumber = getStartPageNumber(pageNumber)+PAGE_GROUP_SIZE-1;
      if (endPageNumber > getTotalPageCount(totalBoardCount)) {
         endPageNumber = getTotalPageCount(totalBoardCount);
      }
      return endPageNumber;
   }
   
   public static int getStartRow(int pageNumber){
      return ((pageNumber - 1) * PAGE_LIST_SIZE);
   }
   
   public static int getEndRow(int pageNumber){
      return (pageNumber * PAGE_LIST_SIZE);
   }
   
}

