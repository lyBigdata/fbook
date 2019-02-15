package com.ouer.fbook.db.bean;

/**
 * @author hetao
 */
public class CstColumn {

    public static class BaseBean {
        public static final String ID = "id";
    }

    public static class BookContent {
        public static final String SourceType="source_type";
        public static final String BookName="book_name";
        public static final String Author="author";
        public static final String Introduce="introduce";
        public static final String Cover="cover";
        public static final String Category="category";
        public static final String LastChapterOrder="last_chapter_order";
        public static final String LastChapterName="last_chapter_name";
        public static final String UpdateAt="update_at";
        public static final String IsFinish="is_finish";
        public static final String Valid="valid";
        public static final String NextAt="next_at";
        public static final String LinkType="link_type";
    }

    public static class BookContentChapter {
        public static final String BookContentId="book_content_id";
        public static final String ChapterOrder="chapter_order";
        public static final String ChapterName="chapter_name";
        public static final String WordsCount="words_count";
        public static final String Content="content";
        public static final String UpdatedAt="updated_at";

    }

    public static class BookSpider {
        public static final String SourceType="source_type";
        public static final String Link="link";
    }

    public static class BookSpiderBook extends BookSpider {
        public static final String LinkType="link_type";
        public static final String NextAt="next_at";
        public static final String Valid="valid";
        public static final String BookName="book_name";
        public static final String UpdateAt="update_at";
    }

    public static class BookSpiderChapter extends BookSpider {
        public static final String BookContentId="book_content_id";
        public static final String ChapterOrder="chapter_order";
        public static final String Valid="valid";
        public static final String UpdateAt="update_at";
        public static final String ChapterName="chapter_name";
    }
    public static class BookViewRecord {
        public static final String BookContentId="book_content_id";
        public static final String ChapterOrder="chapter_order";
        public static final String ChapterName="chapter_name";
        public static final String Page="page";
        public static final String UpdateAt="update_at";
        public static final String IsShelf="isShelf";


    }
}
