import { keyframes, styled } from "styled-components";

type Props = {
  size?: string;
};

export default function LoadingIndicator({ size = "48px" }: Props) {
  return <Indicator $size={size}></Indicator>;
}

const RotateAnimation = keyframes`
    0% {
        transform: rotate(0deg);
    }
    100% {
        transform: rotate(360deg);
    }
`;

const Indicator = styled.span<{ $size: string }>`
  width: ${({ $size }) => $size};
  height: ${({ $size }) => $size};
  border: ${({ theme }) =>
    `5px solid ${theme.colorSystem.neutral.text.default}`};
  border-bottom-color: transparent;
  border-radius: 50%;
  display: inline-block;
  box-sizing: border-box;
  animation: ${RotateAnimation} 1s linear infinite;
`;
