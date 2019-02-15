package com.ouer.fbook.spider.site;



import com.ouer.fbook.db.bean.BookContent;
import com.ouer.fbook.db.bean.BookContentChapter;
import com.ouer.fbook.db.bean.BookSpiderBook;
import com.ouer.fbook.db.bean.BookSpiderChapter;
import com.ouer.fbook.spider.model.CategoryEnum;
import com.ouer.fbook.spider.model.ParseBookInfo;
import com.ouer.fbook.spider.model.SiteEnum;
import com.ouer.fbook.spider.model.SpiderType;
import com.ouer.fbook.spider.model.ValidStatus;
import com.ouer.fbook.util.DateUtils;
import com.ouer.fbook.util.StringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

/**
 * @author hetao
 * @date 2019/1/9
 */
public class YGWHParse extends BaseParse {

    @Override
    public SiteEnum getKey() {
        return SiteEnum.YANGGUI;
    }


    @Override
    public ParseBookInfo parseBook(String body) {
        BookContent bookContent = new BookContent();

        Document doc = Jsoup.parse(body, "https://www.yangguiweihuo.com");
        Elements metas = doc.head().select("meta");
        for (Element meta : metas) {
            String content = meta.attr("content");
            if ("og:description".equalsIgnoreCase(meta.attr("property"))) {
                bookContent.setIntroduc(content);
            } else if("og:image".equalsIgnoreCase(meta.attr("property"))) {
                bookContent.setCover(content);
            } else if("og:novel:author".equalsIgnoreCase(meta.attr("property"))) {
                bookContent.setAuthor(content);
            } else if("og:novel:book_name".equalsIgnoreCase(meta.attr("property"))) {
                bookContent.setBookName(content);
            } else if("og:novel:status".equalsIgnoreCase(meta.attr("property"))) {
                if(content.equalsIgnoreCase("连载")) {
                    bookContent.setIsFinish(0);
                } else {
                    bookContent.setIsFinish(1);
                }
            } else if("og:novel:update_time".equalsIgnoreCase(meta.attr("property"))) {
                bookContent.setUpdateAt(DateUtils.getDateFromYyyyMMddHHmmss(content));
            } else if("og:novel:category".equalsIgnoreCase(meta.attr("property"))) {
                bookContent.setCategory(conventCategory(content));
            }
        }
        bookContent.setSourceType(SiteEnum.YANGGUI.name());
        List<BookSpiderChapter> chapterList=parseV2(doc);

        ParseBookInfo bookInfo = new ParseBookInfo();
        bookInfo.setBookContent(bookContent);
        bookInfo.setChapterList(chapterList);

        return bookInfo;
    }


    private List<BookSpiderChapter> parseV1(Document doc) {
        Map<String, BookSpiderChapter> map = new HashMap<>(4);
        Elements metas = doc.select("div.listmain dl dd a");
        List<BookSpiderChapter> chapterList = new ArrayList<>();
        for (Element meta : metas) {

            BookSpiderChapter chapter = new BookSpiderChapter();
            String href = meta.attr("href").trim();

            chapter.setLink(getBaseUrl()+href);
            chapter.setChapterOrder(getHtmlIndex(href));
            map.put(href, chapter);
        }

        for (String key : map.keySet()) {
            chapterList.add(map.get(key));
        }
        Collections.sort(chapterList, new Comparator<BookSpiderChapter>() {
            @Override
            public int compare(BookSpiderChapter o1, BookSpiderChapter o2) {
                return o1.getChapterOrder().compareTo(o2.getChapterOrder());
            }
        });

        for (int i=0; i<chapterList.size(); i++) {
            chapterList.get(i).setChapterOrder(i);
        }
        return chapterList;
    }


    private List<BookSpiderChapter> parseV2(Document doc) {
        Element totalEle = doc.select("div.listmain dl").first();
        int ddStart=0;
        List<BookSpiderChapter> chapterList = new ArrayList<>();
        for (Element element : totalEle.children()) {
            if(ddStart < 2) {
                String tag = element.nodeName();
                if(tag.equalsIgnoreCase("dt")) {
                    ddStart++;
                }
            } else {
                Element linkEle = element.select("a").first();

                BookSpiderChapter chapter = new BookSpiderChapter();
                String href = linkEle.attr("href").trim();

                chapter.setLink(getBaseUrl()+href);
                chapter.setChapterOrder(getHtmlIndex(href));

                String title = linkEle.text().trim();
                chapter.setChapterName(title);
                chapterList.add(chapter);
            }
        }

        for (int i=0; i<chapterList.size(); i++) {
            chapterList.get(i).setChapterOrder(i);
        }
        return chapterList;
    }

    @Override
    public BookContentChapter parseChapter(String body) {

        BookContentChapter chapter = new BookContentChapter();
        Document doc = Jsoup.parse(body, "https://www.yangguiweihuo.com");
        Element titleElement = doc.select("div.content h1").first();
        chapter.setChapterName(titleElement.text());

        Element contentElement = doc.select("div.content div.showtxt").first();

        String content = contentElement.html();
//        content = content.replace("<br>", "");
        content = StringUtils.substringBefore(content, "https://www.yangguiweihuo.com");
        content=content.replace("&nbsp;&nbsp;", " ");
        content=content.replace("<br>", "");

        chapter.setContent(content);
        chapter.setWordsCount((long)content.length());
        chapter.setUpdatedAt(new Date());
        return chapter;
    }

    public List<BookSpiderBook> parseRank(String body) {

        Document doc = Jsoup.parse(body, "https://www.yangguiweihuo.com");
        Elements links = doc.select("ul.tli li");

        Map<String, BookSpiderBook> bookMap = new HashMap<>(4);
        for (Element link : links) {

            Element element = link.select("a").first();
            String href = element.attr("href").trim();
            String title = element.text();
            String type = link.select("span").first().text().trim();

//            if(!bookMap.containsKey(title)) {
//                BookSpiderBook bookSpiderBook = new BookSpiderBook();
//                bookSpiderBook.setNextAt(new Date());
//                bookSpiderBook.setValid(ValidStatus.READY.value());
//                bookSpiderBook.setSourceType(SiteEnum.YANGGUI.name());
//                bookSpiderBook.setLinkType(SpiderType.CHAPTER.name());
//                bookSpiderBook.setLink(getBaseUrl() + href);
//                bookSpiderBook.setBookName(title);
//                bookMap.put(title, bookSpiderBook);
//            }
        }

        List<BookSpiderBook> bookList = new ArrayList<>();
        for (String key :bookMap.keySet()) {
            bookList.add(bookMap.get(key));
        }
        return bookList;
    }


    @Override
    public List<BookSpiderBook> findBookByCategory(String body) {
        Document doc = Jsoup.parse(body, "https://www.yangguiweihuo.com");
        Elements bookList = doc.select("span.s2 a");

        List<BookSpiderBook> spiderBookList = new ArrayList<>();
//        for (Element book : bookList) {
//            BookSpiderBook spiderBook = new BookSpiderBook();
//
//            String link = book.attr("href").trim();
//            String bookName=book.text().trim();
//
//            spiderBook.setLinkType(SpiderType.CHAPTER.name());
//            spiderBook.setLink(getBaseUrl()+link);
//            spiderBook.setSourceType(SiteEnum.YANGGUI.name());
//            spiderBook.setValid(ValidStatus.READY.value());
//            spiderBook.setBookName(bookName);
//            spiderBook.setNextAt(new Date());
//            spiderBookList.add(spiderBook);
//        }

        return spiderBookList;
    }

    @Override
    public List<BookContent> searchBook(String body) {
        Document doc = Jsoup.parse(body, "https://www.yangguiweihuo.com");
        Elements bookList = doc.select("div.type_show div.bookbox");

        List<BookContent> spiderBookList = new ArrayList<>(4);
        for (Element book : bookList) {
            BookContent spiderBook = new BookContent();
            String href = book.select("div.bookimg a").first().attr("href").trim();
            spiderBook.setLink(getBaseUrl()+href);

            String img = book.select("div.bookimg a img").first().attr("src").trim();
            spiderBook.setCover(getBaseUrl()+img);
            String bookName = book.select("h4.bookname").first().text().trim();
            spiderBook.setBookName(bookName);

            String category = book.select("div.cat").first().text().trim();
            spiderBook.setCategory(category);

            String author = book.select("div.author").first().text().trim();
            spiderBook.setAuthor(author);

            String lastChapter = book.select("div.update").first().text().trim();
            spiderBook.setLastChapterName(lastChapter);

            spiderBookList.add(spiderBook);
        }

        return spiderBookList;
    }

    @Override
    public String getBaseUrl() {
        return "https://www.yangguiweihuo.com";
    }

    private String conventCategory(String categoryCn) {
        return CategoryEnum.parseDesc(categoryCn).name();
    }
}
