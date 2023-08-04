import { css, styled } from "styled-components";
import { ReactComponent as AlertCircle } from "../assets/alertCircle.svg";
import { ReactComponent as Archive } from "../assets/archive.svg";
import { ReactComponent as Calendar } from "../assets/calendar.svg";
import { ReactComponent as CheckBoxActive } from "../assets/checkBoxActive.svg";
import { ReactComponent as CheckBoxDisable } from "../assets/checkBoxDisable.svg";
import { ReactComponent as CheckBoxInitial } from "../assets/checkBoxInitial.svg";
import { ReactComponent as CheckOffCircle } from "../assets/checkOffCircle.svg";
import { ReactComponent as CheckOnCircle } from "../assets/checkOnCircle.svg";
import { ReactComponent as ChevronDown } from "../assets/chevronDown.svg";
import { ReactComponent as Comment } from "../assets/comment.svg";
import { ReactComponent as Edit } from "../assets/edit.svg";
import { ReactComponent as Label } from "../assets/label.svg";
import { ReactComponent as LogoDarkLarge } from "../assets/logoDarkLarge.svg";
import { ReactComponent as LogoDarkMedium } from "../assets/logoDarkMedium.svg";
import { ReactComponent as LogoLightLarge } from "../assets/logoLightLarge.svg";
import { ReactComponent as LogoLightMedium } from "../assets/logoLightMedium.svg";
import { ReactComponent as Milestone } from "../assets/milestone.svg";
import { ReactComponent as PaperClip } from "../assets/paperclip.svg";
import { ReactComponent as Plus } from "../assets/plus.svg";
import { ReactComponent as RefreshCcw } from "../assets/refreshCcw.svg";
import { ReactComponent as Search } from "../assets/search.svg";
import { ReactComponent as Smile } from "../assets/smile.svg";
import { ReactComponent as Trash } from "../assets/trash.svg";
import { ReactComponent as UserImageLarge } from "../assets/userImageLarge.svg";
import { ReactComponent as UserImageSmall } from "../assets/userImageSmall.svg";
import { ReactComponent as XSquare } from "../assets/xSquare.svg";
import { cloneElement } from "react";

export function Icon({
  name,
  fill,
  stroke,
}: {
  name: string;
  fill?: string;
  stroke?: string;
}) {
  const isSvgExist = getIcon(name) !== null;

  return (
    <>
      {isSvgExist && (
        <IconWrapper $fillType={fillTypes.includes(name)}>
          {cloneElement(getIcon(name)!, {fill, stroke})}
        </IconWrapper>
      )}
    </>
  );
}

const fillTypes = ["milestone", "userImageLarge", "userImageSmall"];

const getIcon = (name: string) => {
  switch (name) {
    case "alertCircle":
      return <AlertCircle />;
    case "archive":
      return <Archive />;
    case "calendar":
      return <Calendar />;
    case "checkBoxActive":
      return <CheckBoxActive />;
    case "checkBoxDisable":
      return <CheckBoxDisable />;
    case "checkBoxInitial":
      return <CheckBoxInitial />;
    case "checkOffCircle":
      return <CheckOffCircle />;
    case "checkOnCircle":
      return <CheckOnCircle />;
    case "chevronDown":
      return <ChevronDown />;
    case "comment":
      return <Comment />;
    case "edit":
      return <Edit />;
    case "label":
      return <Label />;
    case "logoDarkMedium":
      return <LogoDarkMedium />;
    case "logoDarkLarge":
      return <LogoDarkLarge />;
    case "logoLightMedium":
      return <LogoLightMedium />;
    case "logoLightLarge":
      return <LogoLightLarge />;
    case "milestone":
      return <Milestone />;
    case "paperclip":
      return <PaperClip />;
    case "plus":
      return <Plus />;
    case "refreshCcw":
      return <RefreshCcw />;
    case "search":
      return <Search />;
    case "smile":
      return <Smile />;
    case "trash":
      return <Trash />;
    case "userImageLarge":
      return <UserImageLarge />;
    case "userImageSmall":
      return <UserImageSmall />;
    case "xSquare":
      return <XSquare />;
    default:
      return null;
  }
};

const IconWrapper = styled.div<{ $fillType: boolean }>`
  display: flex;
  align-items: center;

  ${({ $fillType }) =>
    $fillType
      ? css`
          & > svg {
            stroke: transparent;
          }
        `
      : css`
          & > svg {
            fill: transparent;
          }
        `};
`;
