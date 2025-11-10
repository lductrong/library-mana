# Library MCP Server

MCP server đơn giản để quản lý thư viện sách.

## Cài đặt

```bash
cd mcp-server
npm install
```

## Cấu hình trong Kiro

Thêm vào file `.kiro/settings/mcp.json`:

```json
{
  "mcpServers": {
    "library": {
      "command": "node",
      "args": ["mcp-server/index.js"],
      "disabled": false,
      "autoApprove": ["list_books", "search_book"]
    }
  }
}
```

## Các tools có sẵn

- `list_books`: Liệt kê tất cả sách
- `search_book`: Tìm kiếm sách theo tên/tác giả
- `borrow_book`: Mượn sách
- `return_book`: Trả sách

## Chạy thử

```bash
npm start
```
