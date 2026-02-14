package eu.kanade.tachiyomi.extension.tr.sadscans

import eu.kanade.tachiyomi.network.GET
import eu.kanade.tachiyomi.source.model.*
import eu.kanade.tachiyomi.source.online.ParsedHttpSource
import okhttp3.Request
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class Sadscans : ParsedHttpSource() {

    override val name = "Sadscans"
    override val baseUrl = "https://sadscans.net"
    override val lang = "tr"
    override val supportsLatest = true

    // ============================================
    // Popular Manga
    // ============================================
    override fun popularMangaRequest(page: Int): Request {
        return GET("$baseUrl/?page=$page", headers)
    }

    override fun popularMangaSelector() = ".manga-carousel, [class*='manga']"

    override fun popularMangaFromElement(element: Element): SManga {
        return SManga.create().apply {
            // Link bul
            val link = element.selectFirst("a")
            
            // Başlık - div içindeki text
            title = element.select("div").text().split("\n").firstOrNull()?.trim() ?: element.text()
            
            // URL
            if (link != null) {
                setUrlWithoutDomain(link.attr("href"))
            }
            
            // Kapak resmi
            val img = element.selectFirst("img")
            if (img != null) {
                thumbnail_url = img.attr("abs:src").ifEmpty { 
                    img.attr("abs:data-src") 
                }
            }
        }
    }

    override fun popularMangaNextPageSelector() = ".pagination .next, a[rel='next'], button[aria-label='Next']"

    // ============================================
    // Latest Updates
    // ============================================
    override fun latestUpdatesRequest(page: Int): Request {
        return GET("$baseUrl/?page=$page", headers)
    }

    override fun latestUpdatesSelector() = popularMangaSelector()
    override fun latestUpdatesFromElement(element: Element) = popularMangaFromElement(element)
    override fun latestUpdatesNextPageSelector() = popularMangaNextPageSelector()

    // ============================================
    // Search
    // ============================================
    override fun searchMangaRequest(page: Int, query: String, filters: FilterList): Request {
        return GET("$baseUrl/?s=$query&page=$page", headers)
    }

    override fun searchMangaSelector() = popularMangaSelector()
    override fun searchMangaFromElement(element: Element) = popularMangaFromElement(element)
    override fun searchMangaNextPageSelector() = popularMangaNextPageSelector()

    // ============================================
    // Manga Details
    // ============================================
    override fun mangaDetailsParse(document: Document): SManga {
        return SManga.create().apply {
            // Başlık - çeşitli selector'lar dene
            title = document.select("h1").text().ifEmpty { 
                document.select(".manga-title, .title, [class*='title']").text() 
            }
            
            // Yazar
            author = document.select(".author, .creator, [class*='author'], [data-author]").text()
            
            // Çizer
            artist = document.select(".artist, [class*='artist']").text()
            
            // Açıklama
            description = document.select(".description, .summary, .synopsis, [class*='summary'], [class*='description'], p").text()
            
            // Kapak resmi
            thumbnail_url = document.select("img").first()?.attr("abs:src") ?: ""
            
            // Kategoriler
            val genres = mutableListOf<String>()
            document.select(".genre, .tag, .badge, [class*='genre'], [class*='tag']").forEach {
                genres.add(it.text())
            }
            genre = genres.joinToString(", ")
            
            // Durum
            val statusText = document.select(".status, [class*='status'], [data-status]").text().lowercase()
            status = when {
                statusText.contains("devam") || statusText.contains("ongoing") || statusText.contains("güncel") -> SManga.ONGOING
                statusText.contains("tamamlandı") || statusText.contains("completed") || statusText.contains("bitti") -> SManga.COMPLETED
                statusText.contains("hiatus") || statusText.contains("ara") -> SManga.ON_HIATUS
                else -> SManga.UNKNOWN
            }
        }
    }

    // ============================================
    // Chapter List
    // ============================================
    override fun chapterListSelector() = "a[href*='chapter'], a[href*='bolum'], .chapter, [class*='chapter'], li, tr"

    override fun chapterFromElement(element: Element): SChapter {
        return SChapter.create().apply {
            // Eğer element zaten bir link ise
            if (element.tagName() == "a") {
                name = element.text()
                setUrlWithoutDomain(element.attr("href"))
            } else {
                // Link içinde bir element ise
                val link = element.selectFirst("a")
                if (link != null) {
                    name = link.text().ifEmpty { element.text() }
                    setUrlWithoutDomain(link.attr("href"))
                } else {
                    name = element.text()
                    setUrlWithoutDomain(element.attr("href"))
                }
            }
            
            // Tarih
            val dateText = element.select(".date, .time, [class*='date'], [class*='time'], time, span").text()
            date_upload = parseChapterDate(dateText)
        }
    }

    // ============================================
    // Page List
    // ============================================
    override fun pageListParse(document: Document): List<Page> {
        val pages = mutableListOf<Page>()
        
        // Çeşitli selector kombinasyonları dene
        val selectors = listOf(
            "img[src*='cdn']",
            "img[data-src*='cdn']",
            ".page-image img",
            ".reader-image",
            "#images img",
            "[class*='page'] img",
            "[class*='reader'] img",
            "img[src*='manga']",
            "img[src*='chapter']"
        )
        
        for (selector in selectors) {
            val images = document.select(selector)
            if (images.isNotEmpty()) {
                images.forEachIndexed { index, element ->
                    val imageUrl = element.attr("abs:src").ifEmpty { 
                        element.attr("abs:data-src").ifEmpty {
                            element.attr("abs:data-lazy-src")
                        }
                    }
                    if (imageUrl.isNotEmpty() && imageUrl.startsWith("http")) {
                        pages.add(Page(index, "", imageUrl))
                    }
                }
                if (pages.isNotEmpty()) break
            }
        }
        
        return pages
    }

    override fun imageUrlParse(document: Document) = ""

    // ============================================
    // Yardımcı Fonksiyonlar
    // ============================================
    private fun parseChapterDate(date: String): Long {
        return try {
            when {
                date.contains("bugün", ignoreCase = true) || date.contains("today", ignoreCase = true) -> {
                    System.currentTimeMillis()
                }
                date.contains("dün", ignoreCase = true) || date.contains("yesterday", ignoreCase = true) -> {
                    System.currentTimeMillis() - 86400000L
                }
                date.contains("saat", ignoreCase = true) || date.contains("hour", ignoreCase = true) -> {
                    val hours = date.filter { it.isDigit() }.toLongOrNull() ?: 1
                    System.currentTimeMillis() - (hours * 3600000L)
                }
                date.contains("gün", ignoreCase = true) || date.contains("day", ignoreCase = true) -> {
                    val days = date.filter { it.isDigit() }.toLongOrNull() ?: 1
                    System.currentTimeMillis() - (days * 86400000L)
                }
                date.contains("hafta", ignoreCase = true) || date.contains("week", ignoreCase = true) -> {
                    val weeks = date.filter { it.isDigit() }.toLongOrNull() ?: 1
                    System.currentTimeMillis() - (weeks * 604800000L)
                }
                date.contains("ay", ignoreCase = true) || date.contains("month", ignoreCase = true) -> {
                    val months = date.filter { it.isDigit() }.toLongOrNull() ?: 1
                    System.currentTimeMillis() - (months * 2592000000L)
                }
                else -> 0L
            }
        } catch (e: Exception) {
            0L
        }
    }
}
