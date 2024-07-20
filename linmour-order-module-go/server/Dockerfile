FROM golang:1.22-alpine AS builder

WORKDIR /app
COPY go.mod .
COPY go.sum .
RUN go mod download

COPY . .
RUN go build -o /app/main .

#生成最小镜像
FROM alpine
COPY --from=builder /app/main /app/main
EXPOSE 12804
CMD ["/app/main"]