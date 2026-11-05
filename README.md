# Hmall — 電商微服務練習專案 / E-commerce Microservices Practice

Hmall 是用於學習 Java 後端與微服務架構的電商練習專案，涵蓋商品、購物車、使用者、交易、付款與 API Gateway 等模組。此程式庫保留課程練習與架構拆分過程中的程式碼，主要用途是技術學習，不代表獨立原創的商業產品或可直接上線的系統。

Hmall is an e-commerce practice project for learning Java backend development and microservice architecture. It covers product, cart, user, trade, payment, and API gateway modules. The repository contains code from coursework and architecture exercises; it is a learning project, not an independently developed commercial product or a production-ready application.

## 學習重點 / Learning Objectives

- **服務拆分**：觀察電商功能如何拆分為不同服務。<br>**Service decomposition**: Explore how e-commerce features are separated into services.
- **服務通訊**：練習 API Gateway、服務註冊與發現、跨服務呼叫。<br>**Service communication**: Practice API gateway routing, service discovery, and inter-service calls.
- **資料處理**：認識資料庫、快取、訊息佇列與分散式交易相關元件。<br>**Data processing**: Study database persistence, caching, messaging, and distributed-transaction components.

## 主要模組 / Main Modules

| 模組 / Module | 用途 / Purpose |
|---|---|
| `hm-service` | 電商服務練習模組 / E-commerce service exercise module |
| `item-service` | 商品服務 / Product service |
| `cart-service` | 購物車服務 / Shopping cart service |
| `user-service` | 使用者服務 / User service |
| `trade-service` | 交易服務 / Trade service |
| `pay-service` | 付款服務 / Payment service |
| `hm-gateway` | API Gateway 與路由 / API gateway and routing |
| `hm-api` | 共用 API 定義 / Shared API definitions |
| `hm-common` | 共用元件與工具 / Shared components and utilities |

## 技術棧 / Technology Stack

- Java 11、Spring Boot 2.7.12、Spring Cloud 2021
- Spring Cloud Gateway、Spring Cloud Alibaba Nacos
- MySQL、MyBatis-Plus、Redis
- RabbitMQ、Seata、Elasticsearch（依模組設定 / depending on module configuration）
- Maven multi-module project

以上項目依專案依賴與設定整理，不代表全部服務皆完成端對端驗證。

The list reflects repository dependencies and configuration; not every integration has been verified end-to-end.

## 本機執行 / Running Locally

本 Repository 為練習快照，**不保證 Clone 後可直接啟動**。根目錄 `pom.xml` 的 `<modules>` 目前有重複項目，須先修正再執行 Maven reactor build。不同模組也可能需要 MySQL、Redis、Nacos、RabbitMQ 等外部服務，以及自己的設定檔。請勿直接使用範例帳密或憑證。

This is a practice snapshot and is **not guaranteed to run immediately after cloning**. The root `pom.xml` currently contains duplicate `<modules>` entries that must be corrected before running a Maven reactor build. Depending on the module, you may also need MySQL, Redis, Nacos, RabbitMQ, and local configuration. Do not reuse sample passwords or keys.

## 使用聲明 / Usage Notes

本專案僅供學習及作品展示，部分內容依課程或教材實作；不應視為完全獨立設計的產品，也不應在未完成安全檢查前部署到公開環境。第三方程式碼、課程材料與素材依其各自授權使用。

This repository documents coursework and technical practice, rather than an entirely original commercial product. Do not deploy it to a public environment without a security review. Third-party code, course materials, and assets remain subject to their respective licenses.
