import { styled } from "styled-components";

export default function Button({
  size,
  type,
  flexible,
  icon,
  disabled,
  selected,
  onClick,
  children,
}: {
  size: "small" | "medium" | "large";
  type: "container" | "outline" | "ghost";
  flexible?: "flexible" | "fixed";
  icon?: string;
  disabled?: boolean;
  selected?: boolean;
  onClick: () => void;
  children: string;
}) {

  const buttonMap = {
    container: ConatinerButton,
    outline: OutlineButton,
    ghost: GhostButton,
  };

  const ButtonComponent = buttonMap[type];

  return (
    <ButtonComponent {...{ size, flexible, selected, disabled, onClick }}>
      <div>
        {icon && <img src={`/src/assets/${icon}.svg`} alt={icon} />}
        <span>{children}</span>
      </div>
    </ButtonComponent>
  );
}

const StyledButton = styled.button<{
  size: "small" | "medium" | "large";
  flexible?: "flexible" | "fixed";
}>`
  width: ${({ size, flexible }) =>
    flexible === "flexible"
      ? "fit-content"
      : size === "large"
      ? "240px"
      : size === "medium"
      ? "184px"
      : "128px"};
  height: ${({ size }) =>
    size === "large" ? "56px" : size === "medium" ? "48px" : "40px"};
  padding: ${({ flexible }) =>
    flexible === "flexible" ? "0 24px 0 24px" : ""};
  border-radius: ${({ theme: { radius }, size }) =>
    size === "large" ? radius.large : radius.medium};
  font: ${({ theme: { font }, size }) => {
    const fontSize = size === "large" ? 20 : size === "medium" ? 16 : 12;

    return font[`availableMedium${fontSize}`];
  }};
  opacity: ${({ theme: { opacity } }) => opacity.default};

  &:hover {
    opacity: ${({ theme: { opacity } }) => opacity.hover};
  }

  &:active {
    opacity: ${({ theme: { opacity } }) => opacity.press};
  }

  &:disabled {
    opacity: ${({ theme: { opacity } }) => opacity.disabled};
  }

  div {
    display: flex;
    align-items: center;
    justify-content: center;
  }

  img {
    padding: 4px;
  }

  span {
    padding: 0 8px 0 8px;
  }
`;

const ConatinerButton = styled(StyledButton)`
  background-color: ${({ theme: { color } }) => color.brandSurfaceDefault};
  color: ${({ theme: { color } }) => color.brandTextDefault};

  img {
    filter: brightness(0) saturate(100%) invert(94%) sepia(32%) saturate(0%) hue-rotate(6deg) brightness(105%) contrast(99%);
  }
`;

const OutlineButton = styled(StyledButton)`
  border: 1px solid ${({ theme: { color } }) => color.brandBorderDefault};
  background-color: ${({ theme: { color } }) => color.brandSurfaceWeak};
  color: ${({ theme: { color } }) => color.brandTextWeak};

  img {
    filter: brightness(0) saturate(100%) invert(30%) sepia(47%) saturate(3393%) hue-rotate(200deg) brightness(103%) contrast(112%);
  }
`


const GhostButton = styled(StyledButton)<{selected?: boolean}>`
  font: ${({ theme: { font }, size, selected }) => {
    const fontSize = size === "large" ? 20 : size === "medium" ? 16 : 12;
    const fontType = selected ? "selectedBold" : "availableMedium";

    return font[`${fontType}${fontSize}`];
  }};
  background-color: ${({ theme: { color } }) => color.brandSurfaceWeak};
  color: ${({ theme: { color }, selected }) =>
    selected ? color.neutralTextStrong : color.neutralTextDefault};

  img {
    filter: ${({ theme: { themeMode }, selected }) =>
      selected
        ? themeMode === "light"
          ? "brightness(0) saturate(100%) invert(8%) sepia(6%) saturate(6138%) hue-rotate(203deg) brightness(94%) contrast(98%)"
          : "brightness(0) saturate(100%) invert(94%) sepia(32%) saturate(0%) hue-rotate(6deg) brightness(105%) contrast(99%)"
        : themeMode === "light"
        ? "brightness(0) saturate(100%) invert(31%) sepia(8%) saturate(1775%) hue-rotate(197deg) brightness(93%) contrast(91%)"
        : "brightness(0) saturate(100%) invert(85%) sepia(17%) saturate(218%) hue-rotate(195deg) brightness(91%) contrast(85%)"};
  }
`;
