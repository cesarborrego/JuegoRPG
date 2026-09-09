---
name: project-juego-rpg
description: High-level facts about the "La Aldea en las Sombras" v1.31 C++17 console RPG project
metadata:
  type: project
---

"La Aldea en las Sombras" / "El Reino de las Sombras" v1.31 — turn-based text RPG in C++17.

**Fact:** Console-only game (stdin/stdout, no GUI, no SDL/SFML). Builds with g++ -std=c++17 across ~19 .cpp files (see .vscode/tasks.json for the canonical compile command). Output binaries: `Reino` (Mac), `Reino.exe`/`JuegoRPG.exe` (Windows). Entry point is `Motor.cpp::main()`.

**Why:** Hobby/learning RPG. User (Cesar) wants to port it to Android (working dir is under androidClaude/).

**How to apply:** No external dependencies — pure stdlib + iostream + <cstdlib> system(). No save-game/file I/O exists (no fstream anywhere). No threads, no chrono, no sleep, no conio/termios. Text contains UTF-8 (accents, ñ, →, ¿) in many data files — relevant for Android TextView encoding.

Module map: Motor (game loop/HUD/navigation), Combate (turn combat), Personajes (stats/inventory), Monstruos(_Data), Habilidades(Data/Monstruo), Efectos(_Data), Armas, Artefactos, Reliquias(Datos), Consumibles, CatalogoObjetos, Loot, Tienda, Utilidades (cross-platform helpers).
