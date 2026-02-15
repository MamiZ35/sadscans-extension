// ========================================
// SADSCANS.NET HTML ANALYZER
// ========================================
// Browser console'da çalıştırın veya browser extension ile kullanın

console.log("🔍 Sadscans.net Selector Analyzer Başlatıldı...\n");

// ========================================
// 1. MANGA CARD ANALYZER
// ========================================
console.log("📦 MANGA CARDS:");
const mangaCards = document.querySelectorAll('a[href*="/manga/"]');
console.log(`   Toplam manga kartı: ${mangaCards.length}`);

if (mangaCards.length > 0) {
    const firstCard = mangaCards[0];
    console.log("\n   İlk kart yapısı:");
    console.log("   - Link:", firstCard.href);
    console.log("   - HTML:", firstCard.outerHTML.substring(0, 200) + "...");
    
    // Başlık bul
    const titleSelectors = [
        '.text-xl.font-bold',
        '.text-2xl.font-bold', 
        '.font-bold.text-white',
        '.line-clamp-2',
        '.line-clamp-1',
        'h1', 'h2', 'h3'
    ];
    
    titleSelectors.forEach(selector => {
        const title = firstCard.querySelector(selector);
        if (title) {
            console.log(`   ✓ Başlık (${selector}):`, title.textContent.trim());
        }
    });
    
    // Resim bul
    const img = firstCard.querySelector('img');
    if (img) {
        console.log("   ✓ Resim src:", img.src || img.dataset.src);
        console.log("   ✓ Resim class:", img.className);
    }
}

// ========================================
// 2. MANGA DETAIL ANALYZER
// ========================================
console.log("\n\n📖 MANGA DETAIL PAGE (eğer detay sayfasındaysanız):");

// Başlık
const titleSelectors = [
    '.text-3xl.font-bold',
    '.text-4xl.font-bold',
    'h1.font-bold',
    'h1.text-white'
];

console.log("   Başlık analizi:");
titleSelectors.forEach(selector => {
    const element = document.querySelector(selector);
    if (element) {
        console.log(`   ✓ ${selector}: "${element.textContent.trim()}"`);
    }
});

// Açıklama
const descriptions = document.querySelectorAll('p:not([class*="line-clamp"])');
console.log(`\n   Açıklama paragrafları: ${descriptions.length}`);
if (descriptions.length > 0) {
    console.log("   İlk paragraf:", descriptions[0].textContent.substring(0, 100) + "...");
}

// Yazar
const authorElement = document.querySelector('*:contains("Yazar")');
if (authorElement) {
    console.log("   ✓ Yazar elementi bulundu");
}

// ========================================
// 3. CHAPTER LIST ANALYZER
// ========================================
console.log("\n\n📚 CHAPTER LIST:");
const chapterLinks = document.querySelectorAll('a[href*="/chapter/"], a[href*="/read/"]');
console.log(`   Toplam bölüm: ${chapterLinks.length}`);

if (chapterLinks.length > 0) {
    const firstChapter = chapterLinks[0];
    console.log("\n   İlk bölüm:");
    console.log("   - Link:", firstChapter.href);
    console.log("   - Text:", firstChapter.textContent.trim());
    console.log("   - HTML:", firstChapter.outerHTML.substring(0, 200) + "...");
    
    // Tarih elementleri
    const dateSelectors = ['.text-xs', '.text-gray-400', '.opacity-70', 'time'];
    dateSelectors.forEach(selector => {
        const date = firstChapter.querySelector(selector);
        if (date) {
            console.log(`   ✓ Tarih (${selector}): "${date.textContent.trim()}"`);
        }
    });
}

// ========================================
// 4. READER PAGE ANALYZER
// ========================================
console.log("\n\n📄 READER PAGE (eğer okuma sayfasındaysanız):");
const readerImages = document.querySelectorAll(
    'img[src*=".jpg"], img[src*=".png"], img[src*=".webp"]'
);
console.log(`   Toplam sayfa resmi: ${readerImages.length}`);

if (readerImages.length > 0) {
    console.log("\n   İlk 3 resim:");
    readerImages.slice(0, 3).forEach((img, i) => {
        console.log(`   ${i + 1}. ${img.src || img.dataset.src}`);
    });
}

// ========================================
// 5. ÖZEL SELECTORlar
// ========================================
console.log("\n\n🎯 ÖNERİLEN SELECTORlar:");
console.log(`
MANGA CARDS:
- Selector: a[href*="/manga/"]
- Başlık: ${mangaCards.length > 0 ? mangaCards[0].querySelector('.font-bold')?.className : 'Bulunamadı'}
- Resim: img.object-cover

CHAPTER LIST:  
- Selector: a[href*="/chapter/"] veya a[href*="/read/"]
- Başlık: .font-semibold veya .text-white
- Tarih: .text-xs veya .text-gray-400

READER:
- Resimler: img[src*=".jpg"], img[src*=".png"], img[src*=".webp"]
`);

console.log("\n✅ Analiz tamamlandı!");
console.log("\n💡 İPUCU: Bu çıktıyı kopyalayıp Sadscans.kt dosyasını güncelleyin.");
