#!/usr/bin/env node
import { Server } from "@modelcontextprotocol/sdk/server/index.js";
import { StdioServerTransport } from "@modelcontextprotocol/sdk/server/stdio.js";
import {
  CallToolRequestSchema,
  ListToolsRequestSchema,
} from "@modelcontextprotocol/sdk/types.js";

// Dữ liệu mẫu cho thư viện
const books = [
  { id: 1, title: "Clean Code", author: "Robert C. Martin", available: true },
  { id: 2, title: "Design Patterns", author: "Gang of Four", available: false },
  { id: 3, title: "The Pragmatic Programmer", author: "Hunt & Thomas", available: true }
];

const server = new Server(
  {
    name: "library-mcp-server",
    version: "1.0.0",
  },
  {
    capabilities: {
      tools: {},
    },
  }
);

// Định nghĩa các tools
server.setRequestHandler(ListToolsRequestSchema, async () => {
  return {
    tools: [
      {
        name: "list_books",
        description: "Liệt kê tất cả sách trong thư viện",
        inputSchema: {
          type: "object",
          properties: {},
        },
      },
      {
        name: "search_book",
        description: "Tìm kiếm sách theo tên hoặc tác giả",
        inputSchema: {
          type: "object",
          properties: {
            query: {
              type: "string",
              description: "Từ khóa tìm kiếm",
            },
          },
          required: ["query"],
        },
      },
      {
        name: "borrow_book",
        description: "Mượn sách từ thư viện",
        inputSchema: {
          type: "object",
          properties: {
            bookId: {
              type: "number",
              description: "ID của sách cần mượn",
            },
          },
          required: ["bookId"],
        },
      },
      {
        name: "return_book",
        description: "Trả sách về thư viện",
        inputSchema: {
          type: "object",
          properties: {
            bookId: {
              type: "number",
              description: "ID của sách cần trả",
            },
          },
          required: ["bookId"],
        },
      },
    ],
  };
});

// Xử lý các tool calls
server.setRequestHandler(CallToolRequestSchema, async (request) => {
  const { name, arguments: args } = request.params;

  switch (name) {
    case "list_books": {
      return {
        content: [
          {
            type: "text",
            text: JSON.stringify(books, null, 2),
          },
        ],
      };
    }

    case "search_book": {
      const query = args.query.toLowerCase();
      const results = books.filter(
        (book) =>
          book.title.toLowerCase().includes(query) ||
          book.author.toLowerCase().includes(query)
      );
      return {
        content: [
          {
            type: "text",
            text: results.length > 0
              ? JSON.stringify(results, null, 2)
              : "Không tìm thấy sách nào",
          },
        ],
      };
    }

    case "borrow_book": {
      const book = books.find((b) => b.id === args.bookId);
      if (!book) {
        return {
          content: [{ type: "text", text: "Không tìm thấy sách" }],
        };
      }
      if (!book.available) {
        return {
          content: [{ type: "text", text: "Sách đã được mượn" }],
        };
      }
      book.available = false;
      return {
        content: [
          {
            type: "text",
            text: `Đã mượn sách: ${book.title}`,
          },
        ],
      };
    }

    case "return_book": {
      const book = books.find((b) => b.id === args.bookId);
      if (!book) {
        return {
          content: [{ type: "text", text: "Không tìm thấy sách" }],
        };
      }
      if (book.available) {
        return {
          content: [{ type: "text", text: "Sách chưa được mượn" }],
        };
      }
      book.available = true;
      return {
        content: [
          {
            type: "text",
            text: `Đã trả sách: ${book.title}`,
          },
        ],
      };
    }

    default:
      throw new Error(`Unknown tool: ${name}`);
  }
});

async function main() {
  const transport = new StdioServerTransport();
  await server.connect(transport);
  console.error("Library MCP Server đang chạy trên stdio");
}

main().catch((error) => {
  console.error("Server error:", error);
  process.exit(1);
});
