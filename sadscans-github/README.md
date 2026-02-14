# 🔍 Sadscans Tachiyomi Extension - Otomatik Derleme

Bu repository, Sadscans.net için Tachiyomi extension'ını **GitHub Actions** ile otomatik olarak derler.

## 🚀 Nasıl Kullanılır?

### 1️⃣ Bu Repository'yi GitHub'a Yükleyin

1. **GitHub.com**'a gidin ve giriş yapın
2. **New Repository** oluşturun
3. İsim: `sadscans-tachiyomi` (veya istediğiniz isim)
4. **Public** seçin
5. **Create Repository**

### 2️⃣ Dosyaları Yükleyin

GitHub repository sayfasında:

1. **Upload files** butonuna tıklayın
2. Bu klasördeki **TÜM dosyaları** sürükleyin:
   ```
   .github/workflows/build.yml
   src/Sadscans.kt
   src/build.gradle.kts
   README.md
   ```
3. **Commit changes** tıklayın

### 3️⃣ APK'yı Derleyin

1. GitHub repository'nizde **Actions** sekmesine gidin
2. **"Build Sadscans Extension"** workflow'unu seçin
3. Sağ tarafta **"Run workflow"** butonuna tıklayın
4. **"Run workflow"** onaylayın
5. ⏳ **2-5 dakika bekleyin** (GitHub otomatik derliyor)

### 4️⃣ APK'yı İndirin

1. Workflow tamamlandığında ✅ yeşil tik görünür
2. Workflow'a tıklayın
3. En altta **"Artifacts"** bölümünde **"sadscans-extension"** göreceksiniz
4. **İndirin!** 📥

### 5️⃣ Telefonunuza Yükleyin

1. İndirdiğiniz ZIP'i açın
2. İçindeki **APK** dosyasını telefonunuza atın
3. Telefondan APK'yı açın
4. **"Bilinmeyen kaynaklara izin ver"** etkinleştirin
5. **Yükle** 🎉

## 📱 Tachiyomi'de Kullanım

1. Tachiyomi'yi açın
2. **Extensions** → **Sadscans** görünmeli
3. **Etkinleştirin**
4. **Browse** → **Sadscans**
5. Manga arayın ve okumaya başlayın! 🎉

## 🔄 Güncellemek İçin

Extension'ı güncellemek isterseniz:

1. GitHub repository'de **Actions** → **Run workflow**
2. Yeni APK indirilecek
3. Eski extension'ı silin, yenisini yükleyin

## ⚠️ Sorun Giderme

### Workflow Başarısız Olursa:

1. **Actions** sekmesinde hataya tıklayın
2. Logları okuyun
3. Genellikle Tachiyomi repository değişikliği olabilir

### APK Artifacts Görünmüyorsa:

1. Workflow'un **tamamlandığından** emin olun (✅ işareti)
2. Sayfayı yenileyin
3. En altta **"Artifacts"** bölümüne bakın

### Extension Çalışmıyorsa:

1. Tachiyomi'yi güncelleyin
2. Extension'ı kaldırıp yeniden yükleyin
3. Site yapısı değişmiş olabilir - bana haber verin!

## 📋 Dosya Yapısı

```
sadscans-tachiyomi/
├── .github/
│   └── workflows/
│       └── build.yml          # GitHub Actions otomatik derleme
├── src/
│   ├── Sadscans.kt           # Ana extension kodu
│   └── build.gradle.kts      # Build yapılandırması
└── README.md                  # Bu dosya
```

## 🎯 Özellikler

- ✅ Otomatik APK derleme
- ✅ Tek tıkla build
- ✅ Artifacts olarak indirme
- ✅ Android Studio gerektirmez
- ✅ Tamamen ücretsiz

## 💡 İpuçları

1. **İlk build 3-5 dakika** sürebilir
2. **Sonraki build'ler daha hızlı** olur (cache sayesinde)
3. **GitHub Actions ücretsiz** (Public repo için)
4. Ayda **2000 dakika ücretsiz** build süresi var

## 🆘 Yardım

Sorun yaşarsanız:

1. **Actions** loglarını kontrol edin
2. **Issues** açarak yardım isteyin
3. Bana ulaşın!

## 📜 Lisans

Tachiyomi extensions lisansı ile uyumludur.

---

**🎉 Artık Android Studio olmadan APK derleyebilirsiniz!**

**Not:** İlk kullanımda GitHub hesabı oluşturmanız gerekiyor (ücretsiz).
