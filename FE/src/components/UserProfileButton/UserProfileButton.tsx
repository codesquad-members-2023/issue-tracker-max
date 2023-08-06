import { styled } from "styled-components";

type Props = {
  src: string;
  size?: "large" | "small";
  onClick?(): void;
};

export default function UserProfileButton({
  src,
  size = "large",
  onClick,
}: Props) {
  return (
    <Button onClick={onClick} $size={size}>
      <UserImg src={src} />
    </Button>
  );
}

const Button = styled.button<{ $size: string }>`
  width: ${({ $size }) => ($size === "small" ? "20px" : "32px")};
  height: ${({ $size }) => ($size === "small" ? "20px" : "32px")};
`;

const UserImg = styled.img`
  width: 100%;
  height: 100%;
  border-radius: ${({ theme }) => theme.radius.half};
`;
