# High load systems lab

Task: https://github.com/andermirik/HighLoad/blob/main/labs/lab1.md 

Team: <br>
Ekaterina Polozova <br>
Polina Prokusheva <br>
Polina Popova <br>

### Subject area
Monsters, Inc. 
### Database architecture

```mermaid
erDiagram
          Roles ||--|{ Users : ""
          Users |o--o| Monster : ""
          Monster }o--o{ Reward : ""
          Monster ||--|{ Fear_action : ""
          Monster |o--o{ Infection : ""        
          Infected_thing ||--|{ Infection : ""
          Fear_action |o--o{ Electric_balloon: ""
          City |o--|{ Electric_balloon: ""
          Door ||--o{ Infected_thing : ""    
          Door ||--|{ Fear_action : ""
          Door ||--|{ Child : ""

```
<div align="right">
  <img src="https://im.wampi.ru/2022/10/01/MAIKf02d4ac4d7f81d9d.png" alt="MAIKf02d4ac4d7f81d9d.png" border="0">
</div>
