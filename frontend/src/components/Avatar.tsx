import { styled } from "styled-components";

type AvatarSize = "S" | "L";

type AvatarProps = {
  src?: string;
  userId?: string;
  size: AvatarSize;
};

export function Avatar({ src, userId, size }: AvatarProps) {
  const generateColor = (userId: string) => {
    let hash = 0;
    for (let i = 0; i < userId.length; i++) {
      hash = userId.charCodeAt(i) + ((hash << 5) - hash);
    }
    let color = "#";
    for (let i = 0; i < 3; i++) {
      const value = (hash >> (i * 8)) & 0xff;
      color += ("00" + value.toString(16)).substr(-2);
    }
    return color;
  };

  return (
    <Div $size={size}>
      {src !== "" && src ? (
        <img src={src} />
      ) : (
        <BaseAvatar fill={userId ? generateColor(userId) : "#DDD"} />
      )}
    </Div>
  );
}

function BaseAvatar({ fill }: { fill: string }) {
  return (
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 340 340">
      <path
        fill={fill}
        d="m169,.5a169,169 0 1,0 2,0zm0,86a76,76 0 1 1-2,0zM57,287q27-35 67-35h92q40,0 67,35a164,164 0 0,1-226,0"
      />
    </svg>
  );
}

const Div = styled.div<{ $size: AvatarSize }>`
  width: ${({ $size }) => ($size === "L" ? "32px" : "20px")};
  height: ${({ $size }) => ($size === "L" ? "32px" : "20px")};
  background: #f7f7fc;
  border-radius: 32px;

  & img,
  svg {
    width: 100%;
    height: 100%;
    border-radius: 32px;
  }
`;
