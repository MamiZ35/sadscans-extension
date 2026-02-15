# Sadscans Tachiyomi Extension

Sadscans.net için Tachiyomi/Mihon extension.

## 🎯 Özellikler

- ✅ Popüler manga listesi
- ✅ En yeni güncellemeler
- ✅ Manga arama
- ✅ Bölüm okuma
- ✅ Cloudflare desteği

## 📦 Kurulum

### Otomatik (GitHub Actions ile)

1. Bu repository'yi fork edin veya clone edin
2. GitHub'da Actions sekmesine gidin
3. "Build Sadscans Extension" workflow'unu çalıştırın
4. Artifacts bölümünden APK'yı indirin

### Manuel (Lokal build)

```bash
# Repository'yi clone edin
git clone https://github.com/YOUR_USERNAME/sadscans-extension.git
cd sadscans-extension

# Gradle wrapper oluşturun (ilk seferinde)
gradle wrapper --gradle-version 8.5

# Build edin
./gradlew :sadscans:assembleRelease

# APK dosyası şurada: src/tr/sadscans/build/outputs/apk/release/
```

## 🔧 Gereksinimler

- JDK 17 veya üstü
- Android SDK (compileSdk 34)
- Gradle 8.5+

## 📱 Tachiyomi/Mihon'a Yükleme

1. APK dosyasını cihazınıza indirin
2. Tachiyomi/Mihon uygulamasını açın
3. **Browse** → **Extensions** → ⚙️ (Ayarlar)
4. "Install from .apk" veya doğrudan APK'ya tıklayın
5. Uygulamayı onaylayın ve yükleyin

## 🏗️ Proje Yapısı

```
sadscans-extension/
├── .github/
│   └── workflows/
│       └── build.yml           # GitHub Actions build script
├── src/
│   └── tr/
│       └── sadscans/
│           ├── build.gradle.kts
│           └── src/
│               └── main/
│                   ├── AndroidManifest.xml
│                   ├── java/.../Sadscans.kt
│                   └── res/
│                       └── mipmap-*/ic_launcher.png
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## 🔍 Sorun Giderme

### Build Hatası: "Gradle version mismatch"
```bash
./gradlew wrapper --gradle-version 8.5
```

### APK oluşmadı
```bash
./gradlew clean
./gradlew :sadscans:assembleRelease --stacktrace
```

### Icon eksik
```bash
python3 create_icons.sh  # Veya manuel olarak iconları ekleyin
```

## 🤝 Katkıda Bulunma

1. Fork edin
2. Feature branch oluşturun (`git checkout -b feature/amazing`)
3. Commit edin (`git commit -m 'Add amazing feature'`)
4. Push edin (`git push origin feature/amazing`)
5. Pull Request açın

## 📄 Lisans

Bu proje Apache 2.0 lisansı altındadır.

## ⚠️ Yasal Uyarı

Bu extension sadece eğitim amaçlıdır. İçerik sağlayıcılarla hiçbir ilişkimiz yoktur.
