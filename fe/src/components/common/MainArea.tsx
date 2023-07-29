export function MainArea({ children }: { children: React.ReactNode }) {
  return (
    <div
      className="mainArea"
      css={{
        display: "flex",
        width: "100%",
        height: "100%",
        boxSizing: "border-box",
        flexDirection: "column",
        padding: "32px 80px",
      }}>
      {children}
    </div>
  );
}
