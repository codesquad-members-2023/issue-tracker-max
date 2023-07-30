import { styled } from "styled-components";

export function InformationTag({
  value,
  size,
  toolTip = "",
  icon,
  fill,
  stroke,
  fontColor = "Dark",
}: {
  value: string;
  size: "M" | "S";
  toolTip?: string;
  icon?: string;
  fill?: string;
  stroke?: "Default" | "DefaultActive";
  fontColor?: "Light" | "Dark";
}) {
  return (
    <StyledInformationTag
      data-title={/^\s*$/.test(toolTip) ? undefined : toolTip}
      $size={size}
      $fill={fill}
      $stroke={stroke}
      $darkFont={fontColor === "Dark"}
    >
      {icon && <img src={`/src/assets/${icon}.svg`} alt={icon} />}
      <span>{value}</span>
    </StyledInformationTag>
  );
}

const StyledInformationTag = styled.div<{
  $size: "M" | "S";
  $fill?: string;
  $stroke?: "Default" | "DefaultActive";
  $darkFont: boolean;
}>`
  display: inline-flex;
  justify-content: center;
  align-items: center;
  width: fit-content;
  height: ${({ $size }) => ($size === "M" ? "32px" : "24px")};
  padding: ${({ $size }) => ($size === "M" ? "0 16px" : "0 8px")};
  border: 1px solid
    ${({ theme, $stroke }) =>
      $stroke && theme.color[`neutralBorder${$stroke}`]
        ? theme.color[`neutralBorder${$stroke}`]
        : "transparent"};
  border-radius: ${({ theme }) => theme.radius.large};
  background-color: ${({ theme, $fill }) =>
    $fill && /^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$/g.test($fill)
      ? $fill
      : theme.color.neutralSurfaceStrong};
  color: ${({ theme, $darkFont }) =>
    $darkFont ? theme.color.neutralTextWeak : theme.color.brandTextDefault};
  font: ${({ theme }) => theme.font.displayMedium12};

  &[data-title] {
    position: relative;
  }

  &[data-title]::after {
    content: attr(data-title);
    position: absolute;
    left: 50%;
    transform: translate(-50%, 100%);
    padding: 4px 8px;
    border-radius: 4px;
    white-space: nowrap;
    background-color: ${({ theme }) => theme.color.neutralTextStrong};
    color: ${({ theme }) => theme.color.neutralSurfaceStrong};
    z-index: 9999;
    opacity: 0;
    visibility: hidden;
  }

  &[data-title]:hover::after {
    opacity: 1;
    visibility: visible;
    transition: all 0.1s ease 0.5s;
  }

  img {
    filter: ${({ theme, $darkFont }) =>
      $darkFont
        ? theme.iconFilter.neutralTextWeak
        : theme.iconFilter.brandTextDefault};
  }

  span {
    padding: 0px 4px;
  }
`;
