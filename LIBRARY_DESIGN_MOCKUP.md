# PlayNarrative Library — Complete Design Mockup
## Philosophy-Driven Gaming Archive

---

## 📱 Full Library Page Layout

```
┌─────────────────────────────────────────────────────────────────────────┐
│ PlayNarrative                                    [Dashboard] [Library]   │
│ personal gaming archive                                                  │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                           │
│  [Currently Playing]  [All Games]              [+ Add Game]  [Wishlist] │
│                                                                           │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                           │
│  ┌──────────────────────┐  ┌────────────────────────────────────────┐ │
│  │                      │  │                                          │ │
│  │  This is what        │  │  🎬 Story (7) ▼                          │ │
│  │  you've been up to   │  │  ──────────────────────────────────────  │ │
│  │                      │  │                                          │ │
│  │  Your active gaming  │  │  ┌──────────┐  ┌──────────┐  ┌──────────┐ │ │
│  │  life, organized    │  │  │ 🎮      │  │ 🎮      │  │ 🎮      │ │ │
│  │  by how you play    │  │  │ Dragon  │  │ Yakuza  │  │ Tales   │ │ │
│  │                      │  │  │ Age    │  │ Like a  │  │ of      │ │ │
│  │  Each game tells    │  │  │        │  │ Dragon  │  │ Arise   │ │ │
│  │  a story — choose   │  │  │ PS5    │  │ PS5     │  │ PS5     │ │ │
│  │  the experience     │  │  │ [Story]│  │ [Story] │  │ [Story] │ │ │
│  │  that fits your     │  │  │        │  │[Comp]   │  │         │ │ │
│  │  mood right now.    │  │  │ 💭 How │  │ 💭 "The │  │ 💭 How  │ │ │
│  │                      │  │  │ do you│  │ combat  │  │ do you  │ │ │
│  ──────────────────────  │  │  │ want │  │ system  │  │ want   │ │ │
│                          │  │  │ to    │  │ is so   │  │ to     │ │ │
│  🎬 You've been         │  │  │ feel? │  │ satis-  │  │ feel?  │ │ │
│     playing Story       │  │  └──────────┘  └──────────┘  └──────────┘ │ │
│                     7   │  │                                          │ │
│                          │  │  ┌──────────┐  ┌──────────┐            │ │
│  🏆 You've been         │  │  │ 🎮      │  │ 🎮      │            │ │
│     focusing on         │  │  │ Zelda   │  │ Final   │            │ │
│     Completion          │  │  │ Skyward │  │ Fantasy │            │ │
│                     4   │  │  │ Sword   │  │ XVI     │            │ │
│                          │  │  │        │  │         │            │ │
│  🎮 You've been         │  │  │ Switch │  │ PS5     │            │ │
│     competing in        │  │  │ [Story]│  │ [Story] │            │ │
│     Online MP           │  │  │[Comp]  │  │         │            │ │
│                     6   │  │  │        │  │ 💭 How  │            │ │
│                          │  │  │ 💭 How│  │ do you  │            │ │
│  🤝 You've been         │  │  │ do you │  │ want   │            │ │
│     sharing Social      │  │  │ want  │  │ to     │            │ │
│                     3   │  │  │ to    │  │ feel?  │            │ │
│                          │  │  └──────────┘  └──────────┘            │ │
│  🌿 You've been         │  │                                          │ │
│     relaxing with       │  │                                          │ │
│     Cozy / Life Sim      │  │                                          │ │
│                     5   │  │                                          │ │
│                          │  │                                          │ │
│  └──────────────────────┘  └────────────────────────────────────────┘ │
│                                                                           │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 🎯 Currently Playing View (Default)

### Left Column: Personal Gaming Dashboard

**Greeting Card:**
```
┌─────────────────────────────┐
│ This is what you've been    │
│ up to                       │
│                             │
│ Your active gaming life,    │
│ organized by how you play   │
│                             │
│ ─────────────────────────── │
│ Each game tells a story —   │
│ choose the experience that  │
│ fits your mood right now.   │
└─────────────────────────────┘
```

**Engagement Type Cards:**
```
┌─────────────────────────────┐
│ 🎬  You've been playing     │
│     Story                    │
│                         7   │
└─────────────────────────────┘

┌─────────────────────────────┐
│ 🏆  You've been focusing on │
│     Completion               │
│                         4   │
└─────────────────────────────┘

┌─────────────────────────────┐
│ 🎮  You've been competing in │
│     Online MP                │
│                         6   │
└─────────────────────────────┘

┌─────────────────────────────┐
│ 🤝  You've been sharing      │
│     Social                    │
│                         3   │
└─────────────────────────────┘

┌─────────────────────────────┐
│ 🌿  You've been relaxing with│
│     Cozy / Life Sim          │
│                         5   │
└─────────────────────────────┘
```

### Right Column: Accordion Sections

**Story Section (Expanded):**
```
┌─────────────────────────────────────────────────────────┐
│ 🎬 Story (7)                                    ▼        │
├─────────────────────────────────────────────────────────┤
│                                                           │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐│
│  │ 🎮       │  │ 🎮       │  │ 🎮       │  │ 🎮       ││
│  │ Dragon   │  │ Yakuza   │  │ Tales    │  │ Zelda    ││
│  │ Age:     │  │ Like a   │  │ of       │  │ Skyward  ││
│  │ Veilguard│  │ Dragon   │  │ Arise    │  │ Sword HD ││
│  │          │  │          │  │          │  │          ││
│  │ PS5      │  │ PS5      │  │ PS5      │  │ Switch   ││
│  │ [Story]  │  │ [Story]  │  │ [Story]  │  │ [Story]  ││
│  │          │  │[Comp]   │  │          │  │[Comp]   ││
│  │          │  │          │  │          │  │          ││
│  │ 💭 How   │  │ 💭 "The  │  │ 💭 How   │  │ 💭 How   ││
│  │ do you   │  │ combat   │  │ do you   │  │ do you   ││
│  │ want to  │  │ system   │  │ want to  │  │ want to  ││
│  │ feel?    │  │ is so    │  │ feel?    │  │ feel?    ││
│  │          │  │ satis-   │  │          │  │          ││
│  │          │  │ fying!"  │  │          │  │          ││
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘│
│                                                           │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐              │
│  │ 🎮       │  │ 🎮       │  │ 🎮       │              │
│  │ Final    │  │ Persona  │  │ Mass     │              │
│  │ Fantasy  │  │ 5 Royal  │  │ Effect   │              │
│  │ XVI      │  │          │  │          │              │
│  │          │  │          │  │          │              │
│  │ PS5      │  │ Switch   │  │ PC       │              │
│  │ [Story]  │  │ [Story]  │  │ [Story]  │              │
│  │          │  │          │  │          │              │
│  │ 💭 How   │  │ 💭 How   │  │ 💭 How   │              │
│  │ do you   │  │ do you   │  │ do you   │              │
│  │ want to  │  │ want to  │  │ want to  │              │
│  │ feel?    │  │ feel?    │  │ feel?    │              │
│  └──────────┘  └──────────┘  └──────────┘              │
└─────────────────────────────────────────────────────────┘
```

**Cozy / Life Sim Section (Collapsed):**
```
┌─────────────────────────────────────────────────────────┐
│ 🌿 Cozy / Life Sim (5)                          ▼        │
└─────────────────────────────────────────────────────────┘
```

---

## 🎮 Game Card Details

### Card with Reflection Notes:
```
┌──────────────────────────┐
│ [Platform Icons]         │
│                          │
│  ┌────────────────────┐ │
│  │                    │ │
│  │   Game Cover       │ │
│  │   Image            │ │
│  │                    │ │
│  │              💭    │ │ ← Reflection Badge
│  │         Reflection │ │
│  └────────────────────┘ │
│                          │
│  Yakuza: Like a Dragon   │
│  2020                    │
│                          │
│  [Story] [Completion]    │
│                          │
│  ┌────────────────────┐ │
│  │ "The combat system │ │ ← Reflection Preview
│  │  is so satisfying!"│ │
│  └────────────────────┘ │
└──────────────────────────┘
```

### Card without Reflection (Intentional Prompt):
```
┌──────────────────────────┐
│ [Platform Icons]         │
│                          │
│  ┌────────────────────┐ │
│  │                    │ │
│  │   Game Cover       │ │
│  │   Image            │ │
│  │                    │ │
│  └────────────────────┘ │
│                          │
│  Dragon Age: Veilguard   │
│  2024                    │
│                          │
│  [Story]                 │
│                          │
│  ┌────────────────────┐ │
│  │ 💭 How do you want │ │ ← Intentional Prompt
│  │    to feel while   │ │
│  │    playing?        │ │
│  └────────────────────┘ │
└──────────────────────────┘
```

---

## 📚 All Games View (Explore More)

```
┌─────────────────────────────────────────────────────────────────────────┐
│  [Currently Playing]  [All Games]              [+ Add Game]  [Wishlist] │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                           │
│  ┌───────────────────────────────────────────────────────────────────┐ │
│  │                                                                     │ │
│  │  Your complete gaming archive                                      │ │
│  │  Browse by platform, status, or engagement type                     │ │
│  │                                                                     │ │
│  └───────────────────────────────────────────────────────────────────┘ │
│                                                                           │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐│
│  │ 🎮       │  │ 🎮       │  │ 🎮       │  │ 🎮       │  │ 🎮       ││
│  │ Game 1   │  │ Game 2   │  │ Game 3   │  │ Game 4   │  │ Game 5   ││
│  │          │  │          │  │          │  │          │  │          ││
│  │ PS5      │  │ Switch   │  │ PC       │  │ PS5      │  │ Xbox     ││
│  │ [Story]  │  │ [Cozy]   │  │ [Online]│  │ [Social] │  │ [Comp]   ││
│  │          │  │          │  │          │  │          │  │          │
│  │ 💭 How   │  │ 💭 "Love │  │ 💭 How   │  │ 💭 How   │  │ 💭 How   ││
│  │ do you   │  │ this     │  │ do you   │  │ do you   │  │ do you   ││
│  │ want to  │  │ game!"   │  │ want to  │  │ want to  │  │ want to  ││
│  │ feel?    │  │          │  │ feel?    │  │ feel?    │  │ feel?    ││
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘  └──────────┘│
│                                                                           │
│  [More games in grid layout...]                                          │
│                                                                           │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 🎨 Visual Design Philosophy

### Color Coding:
- **Story**: Blue accent (#60a5fa)
- **Completion**: Gold accent (#fbbf24)
- **Online MP**: Green accent (#34d399)
- **Social**: Purple accent (#c084fc)
- **Cozy / Life Sim**: Light green accent (#86efac)
- **Reflection**: Purple theme (rgba(192, 132, 252, 0.9))

### Typography Hierarchy:
1. **Greeting Title**: 1.125rem, bold, primary color
2. **Engagement Cards**: 0.875rem descriptive text, 1rem type name
3. **Counts**: 1.5rem, bold, orange with glow
4. **Card Titles**: Standard, clear hierarchy
5. **Reflection Text**: 0.8125rem, italic, muted

### Spacing & Layout:
- **Left Column**: 280px fixed width, sticky
- **Right Column**: Flexible, responsive grid
- **Card Spacing**: 1rem gap between cards
- **Section Spacing**: 1.5rem between accordions

---

## 💭 User Experience Flow

### Scenario 1: User wants a story game
1. User opens library → sees greeting "This is what you've been up to"
2. Scans left column → sees "🎬 You've been playing Story (7)"
3. Clicks Story card → Story accordion expands on right
4. Sees 7 story games, some with reflection notes
5. Clicks game with reflection → sees personal note: "The combat system is so satisfying!"
6. Decision made → clicks to view details or start playing

### Scenario 2: User wants to add reflection
1. User sees game card with prompt: "💭 How do you want to feel while playing?"
2. Clicks prompt → navigates to game detail page
3. Adds reflection note in session notes field
4. Returns to library → sees reflection badge and preview on card
5. Card now shows personal narrative element

### Scenario 3: User has many active games (50+)
1. User opens library → sees all engagement types with counts
2. Story section shows 20 games → accordion is expanded
3. User scrolls through Story games → manageable, scannable
4. Other sections collapsed → reduces visual overwhelm
5. User clicks Cozy section → Story collapses, Cozy expands
6. Focused view → no endless scrolling

---

## 🎯 Key Philosophy → Design Mappings

| Philosophy Element | Design Implementation |
|-------------------|----------------------|
| **Intentional Play** | Engagement type cards with conversational labels, prominent counts |
| **Mental Health & Calm** | Collapsible sections, lightweight cards, optional prompts (no pressure) |
| **Personal Narrative** | Reflection badges, note previews, story-driven interface |
| **Scannability** | Accordion sections, clear hierarchy, color-coded types |
| **Unique Identity** | Icon-driven, color-coded, wellness-focused visual language |

---

## 📊 Scalability Examples

### 50+ Currently Playing Games:
- Story: 20 games → Accordion expanded, scrollable
- Completion: 15 games → Accordion expanded, scrollable
- Online MP: 10 games → Accordion expanded, scrollable
- Social: 3 games → Accordion expanded, small section
- Cozy: 5 games → Accordion expanded, small section

### 300+ Archived Games (All Games view):
- Grid layout with pagination or infinite scroll
- Filter by platform, status, engagement type
- Search functionality
- Lightweight cards, no overwhelming lists

---

## ✨ Unique Selling Points

1. **Not Generic**: Engagement types are primary, not alphabetical/platform
2. **Visual Identity**: Icons, colors, and conversational language
3. **Mental Health Focus**: Reflection prompts, no pressure, calm UX
4. **Personal Narrative**: Each game can tell a story through reflection
5. **Scalable**: Works with 5 games or 500 games
6. **Portfolio Ready**: Demonstrates philosophy → design translation

---

This design makes PlayNarrative instantly recognizable as a wellness-driven, intentional gaming archive, not just another library app.
