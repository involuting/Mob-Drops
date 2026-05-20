# Mobdrops

> A powerful, fully customizable mob loot system for Minecraft servers.

Mobdrops gives you complete control over what mobs drop, how often they drop it, and where those drops apply. Built for performance, flexibility, and ease of use, it’s perfect for survival, RPG, and economy-based servers.

---

##  Features

### Core System
- Fully custom mob drops per entity type
- Percentage-based drop chances
- Support for custom items (name, lore, etc.)
- Lightweight and optimized for performance

### Advanced Drop Control
- 🌎 **World-based drops**
- 📍 **Region-based drops**
- 🧠 Smart fallback system (Region → World → Global)

### Categorized Mob Menu
- Browse mobs by categories:
  - 🔴 Hostile
  - 🟢 Passive
  - 🔵 Aquatic
  - 🟣 Bosses
- Clean and modern GUI layout

### 🔍 Search System
- Search mobs directly from the GUI
- Chat-based input system
- Quickly find any mob in seconds

### GUI Management
- Fully in-game drop editor
- Add/remove drops without touching config
- Pagination support for large lists
- Clean navigation system

### Config Support
- Easy-to-edit configuration files
- Auto-loading of drops on startup
- Supports rarity system


## Commands

| Command | Description |
|--------|------------|
| `/mobdrop` | Open main menu |
| `/mobdrop add` | Add drops to a mob |
| `/mobdrop reload` | Reload config |
| `/mobdrop remove` | Remove drops to a mob |



## Permissions

| Permission | Description |
|-----------|------------|
| `op` | Access all commands |

---

## Configuration

Example:
```yaml
mobs:
  ZOMBIE:
    drops:
      - material: DIAMOND
        name: "&bZombie Gem"
        lore:
          - "&7Rare drop"
        amount: 1
        chance: 5.0
        rarity: RARE
```



## How It Works

Mobdrops uses a layered system:

```
Region Drops → World Drops → Global Drops
```

If a mob has drops in a region, those are used.  
If not, it falls back to world drops, then global drops.

## Why Mobdrops?

- No more messy configs
- No more limited drop systems
- Full control with a clean UI
- Built for real servers, not just testing



## Version

**Current Version:** `1.1`

### 🆕 Update 1.1
- Added mob categories
- Added search system
- Improved GUI layout
- Cleaner navigation
- Better filtering system



## Support

If you need help or want to suggest features:
- Open an issue on GitHub
- Or contact via your preferred platform



## 📜 License

MIT License



## Credits

Developed by **Velion Developments / involuting**
