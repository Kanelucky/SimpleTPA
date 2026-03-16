# SimpleTPA

A lightweight and simple **tpa plugin** for **AllayMC**

**SimpleTPA** allows players to send teleport requests, accept or deny them, and manage teleport interactions easily.

---
## Commands
| Command               | Description                       |
|-----------------------|-----------------------------------|
| `/tpahelp`            | Show a list of simpletpa commands |
| `/tpa <player>`       | Send a teleport request           |
| `/tpahere <player>`   | Ask player to teleport to you     |
| `/tpaaccept`          | Accept teleport request           |
| `/tpadeny`            | Deny teleport request             |
| `/tpablock <player>`  | Block TPA requests                |
| `/tpaunlock <player>` | Unblock TPA requests              |
| `/tpareload`          | Reload plugin config              |

---
# Configuration (config.yml)
- The plugin uses a `config.yml` file located in the `plugins/SimpleTPA/` directory. On first run, it will automatically create a default configuration file.

## config.yml Structure
```yaml
messages:
  request-sent: '&aTeleport request sent to {player}'
  request-sent-here: '&aTeleport request sent to {player}'
  request-received: '&e{player} wants to teleport to you.'
  request-received-here: '&e{player} wants you to teleport to them.'
  request-instruction: '&eUse /tpaaccept to accept or /tpadeny to deny.'
  accept-teleporting: '&aTeleporting to {player}...'
  accept-teleported-to-you: '&a{player} teleported to you.'
  accept-you-teleported: '&aTeleporting to {player}...'
  accept-player-teleported: '&a{player} teleported to you.'
  deny-success: '&eYou denied the teleport request.'
  deny-notified: '&c{player} denied your teleport request.'
  block-success: '&aBlocked {player} from sending teleport requests.'
  block-already: '&e{player} is already blocked.'
  unblock-success: '&aUnblocked {player}.'
  unblock-not-blocked: '&e{player} is not blocked.'
  error-no-request: '&cYou don''t have any pending teleport requests.'
  error-request-expired: '&cThe teleport request has expired.'
  error-player-offline: '&cThe player is no longer online.'
  error-already-pending: '&cThis player already has a pending request.'
  error-blocked: '&cThis player has blocked your teleport requests.'
  error-self-teleport: '&cYou cannot send a teleport request to yourself!'
  error-self-block: '&cYou cannot block yourself!'
```
---
# Permissions

`tpahelp`
- Permission: `simpletpa.tpahelp.use`

`tpa <player>`
- Permission: `simpletpa.tpa.use`

`tpaaccept`
- Permission: `simpletpa.tpaaccept.use`

`tpadeny`
- Permission: `simpletpa.tpadeny.use`

`tpahere`
- Permission: `simpletpa.tpahere.use`

`tpablock <player>`
- Permission: `simpletpa.tpablock.use`

`tpaunblock <player>`
- Permission: `simpletpa.tpaunblock.use`

`tpareload`
- Permission: `simpletpa.tpareload.use`
---