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
  return (
    <StyledButton {...{ size, type, flexible, selected, disabled, onClick }}>
      <div>
        {icon && <img src={`/src/assets/${icon}.svg`} alt={icon} />}
        <span>{children}</span>
      </div>
    </StyledButton>
  );
}

const StyledButton = styled.button<{
  size: "small" | "medium" | "large";
  type: "container" | "outline" | "ghost";
  flexible?: "flexible" | "fixed";
  selected?: boolean;
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
  border: ${({ theme: { color }, type }) =>
    type === "outline" ? `1px solid ${color.brandBorderDefault}` : "none"};
  border-radius: ${({ theme: { radius }, size }) =>
    size === "large" ? radius.large : radius.medium};
  font: ${({ theme: { font }, size, selected }) => {
    const fontSize = size === "large" ? 20 : size === "medium" ? 16 : 12;
    const fontType = selected ? "selectedBold" : "availableMedium";

    return font[`${fontType}${fontSize}`];
  }};
  background-color: ${({ theme: { color }, type }) =>
    type === "container" ? color.brandSurfaceDefault : color.brandSurfaceWeak};
  color: ${({ theme: { color }, type, selected }) =>
    type === "container"
      ? color.brandTextDefault
      : type === "outline"
      ? color.brandTextWeak
      : selected
      ? color.neutralTextStrong
      : color.neutralTextDefault};
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
    filter: ${({ theme: { themeMode }, type, selected }) =>
      "brightness(0) saturate(100%) " +
      (type === "container"
        ? "invert(94%) sepia(32%) saturate(0%) hue-rotate(6deg) brightness(105%) contrast(99%)"
        : type === "outline"
        ? "invert(30%) sepia(47%) saturate(3393%) hue-rotate(200deg) brightness(103%) contrast(112%)"
        : selected
        ? themeMode === "light"
          ? "invert(8%) sepia(6%) saturate(6138%) hue-rotate(203deg) brightness(94%) contrast(98%)"
          : "invert(94%) sepia(32%) saturate(0%) hue-rotate(6deg) brightness(105%) contrast(99%)"
        : themeMode === "light"
        ? "invert(31%) sepia(8%) saturate(1775%) hue-rotate(197deg) brightness(93%) contrast(91%)"
        : "invert(85%) sepia(17%) saturate(218%) hue-rotate(195deg) brightness(91%) contrast(85%)")};
  }

  span {
    padding: 0 8px 0 8px;
  }
`;
