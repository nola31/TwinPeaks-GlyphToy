# GitHub Setup Instructions 🚀

## 1. Создать репозиторий на GitHub

1. Зайди на github.com → "New repository"
2. **Repository name:** `TwinPeaks-GlyphToy`
3. **Description:** `Twin Peaks-inspired Glyph Matrix toy - Completely useless but damn fine glyph ☕`
4. **Public** (чтобы комьюнити видело)
5. **НЕ добавляй** README, .gitignore, LICENSE (у нас уже есть)
6. Create repository

## 2. Загрузить проект

Из папки "Damn fine glyph" выполни команды:

```bash
git init
git add .
git commit -m "Initial commit: Twin Peaks Glyph Toy v1.0"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/TwinPeaks-GlyphToy.git
git push -u origin main
```

Замени `YOUR_USERNAME` на свой GitHub username!

## 3. Загрузить GIF демо

**Вариант A (рекомендую):**
1. На GitHub → твой репозиторий → Issues → New Issue
2. В поле описания перетащи свой GIF файл
3. GitHub автоматом создаст ссылку типа: `https://github.com/user-attachments/assets/...`
4. Скопируй эту ссылку
5. Закрой Issue (не сохраняй)

**Вариант B:**
1. Создай папку `/demo` в репозитории
2. Загрузи туда GIF через web interface
3. Ссылка будет: `https://github.com/YOUR_USERNAME/TwinPeaks-GlyphToy/blob/main/demo/demo.gif`

## 4. Обновить README

В файле `README.md` замени строку:
```markdown
![Twin Peaks Glyph Demo](REPLACE_WITH_YOUR_GIF_URL)
```

На:
```markdown
![Twin Peaks Glyph Demo](твоя_ссылка_на_gif)
```

Коммит:
```bash
git add README.md
git commit -m "Add demo GIF"
git push
```

## 5. Создать Release v1.0

1. На GitHub → Releases → "Create a new release"
2. **Tag:** `v1.0`
3. **Release title:** `Twin Peaks Glyph Toy v1.0 - Initial Release`
4. **Description:**
```markdown
## Twin Peaks Glyph Toy 🔥

Two animated patterns inspired by Twin Peaks:
- **Zigzag Pattern** - Red Room floor chevrons flowing upward
- **Owl Symbol Glitch** - Flickering owl symbol with brightness gradients (0-2047)

### Features:
- Switch patterns with Long Press
- Smooth animations optimized for Phone (3)
- Production-ready clean code

*Completely useless but damn fine glyph* ☕
```

5. Attach file: загрузи `TwinPeaks_Final_v7.0.zip` (последний архив)
6. **Publish release**

## 6. Добавить Topics

На главной странице репозитория → Settings → About → Topics:
- `nothing-phone`
- `glyph-matrix`
- `twin-peaks`
- `android`
- `kotlin`
- `glyph-toy`

## 7. Поделиться на nothing.community

Когда всё готово на GitHub, иди на nothing.community и создай пост!

Я помогу написать текст поста! 😸

---

**ВАЖНО:** Проверь что всё работает локально перед пушем!
