package ren.epub.nettyapp.utils;

public class PageUtils {

    private static final Integer PAGE = 0;

    private static final Integer SIZE = 10;

    private Integer page = 0;

    private Integer size = 15;

    public PageUtils(Integer page, Integer size) {

        if (page<0){
            this.page = PAGE;
            this.size = SIZE;
        }

        if (size<1){
            this.size = SIZE;
        }else {
            if(page!=0){
                this.page = page*size;
            }
            this.size = size;
        }

    }

    public Integer getPage() {
        return page<0?PAGE:page;
    }


    public Integer getSize() {
        return size<1?SIZE:size;
    }

    public static Long getTotalPage(Integer size,Long count){
        long totalPage = (long) Math.ceil((double)count/size);
        if (totalPage<=0){
            totalPage = 1L;
        }
        return totalPage;
    }
}
