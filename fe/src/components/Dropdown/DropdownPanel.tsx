import { useEffect } from "react";
import { styled } from "styled-components";
import DropdownItem from "./DropdownItem";
import { DropdownNameKOR, DropdownPanelType } from "./types";

export default function DropdownPanel({
  variant,
  dropdownName,
  dropdownOption,
  dropdownList,
  valueType,
  onOutsideClick,
  position,
}: DropdownPanelType) {
  useEffect(() => {
    document.addEventListener("click", onOutsideClick);

    return () => {
      document.removeEventListener("click", onOutsideClick);
    };
  });

  const generatePanel = () => {
    const canBeNegatory = dropdownName !== "author" && dropdownName !== "issue";
    const suffixKOR =
      dropdownName === "assignee" || dropdownName === "issue" ? "가" : "이";

    // TODO: 중복 제거하기 DropdownItem 수정사항 발생 시 3번 수정해야함
    switch (variant) {
      case "filter":
        return (
          <>
            <Header>
              <h4>{DropdownNameKOR[dropdownName]} 필터</h4>
            </Header>
            <DropdownList>
              {canBeNegatory && (
                <DropdownItem
                  option={dropdownOption}
                  valueType="name"
                  item={{
                    id: 0,
                    variant: "plain",
                    name: "no",
                    content: `${DropdownNameKOR[dropdownName]}${suffixKOR} 없는 이슈`,
                  }}
                />
              )}
              {dropdownList.map((item) => {
                return (
                  <DropdownItem
                    {...{
                      key: item.id,
                      option: dropdownOption,
                      item,
                      valueType,
                    }}
                  />
                );
              })}
            </DropdownList>
          </>
        );
      case "select":
        return (
          <>
            <Header>
              <h4>{DropdownNameKOR[dropdownName]} 설정</h4>
            </Header>
            <DropdownList>
              {dropdownList.map((item) => {
                return (
                  <DropdownItem
                    {...{
                      key: item.id,
                      option: dropdownOption,
                      item,
                      valueType,
                    }}
                  />
                );
              })}
            </DropdownList>
          </>
        );
      case "modify":
        return (
          <>
            <Header>
              <h4>{DropdownNameKOR[dropdownName]} 변경</h4>
            </Header>
            <DropdownList>
              {dropdownList.map((item) => {
                return (
                  <DropdownItem
                    {...{
                      key: item.id,
                      option: dropdownOption,
                      item,
                      valueType,
                    }}
                  />
                );
              })}
            </DropdownList>
          </>
        );
      default:
        throw Error("Invalid panel type");
    }
  };

  return (
    <StyledDropdownPanel $position={position}>
      {generatePanel()}
    </StyledDropdownPanel>
  );
}

const StyledDropdownPanel = styled.div<{ $position: "left" | "right" }>`
  width: 240px;
  display: flex;
  flex-direction: column;
  position: absolute;
  top: 40px;
  left: ${({ $position }) => $position === "left" && "-24px"};
  right: ${({ $position }) => $position === "right" && 0};
  border: ${({ theme: { border, neutral } }) =>
    `${border.default} ${neutral.border.default}`};
  border-radius: ${({ theme: { radius } }) => radius.l};
  box-shadow: ${({ theme: { boxShadow } }) => boxShadow};
  overflow: hidden;
  z-index: 1;
`;

const Header = styled.header`
  width: 100%;
  padding: 8px 16px;
  background-color: ${({ theme: { neutral } }) => neutral.surface.default};
  border-bottom: ${({ theme: { border, neutral } }) =>
    `${border.default} ${neutral.border.default}`};
  font: ${({ theme: { font } }) => font.displayMD12};

  h4 {
    color: ${({ theme: { neutral } }) => neutral.text.weak};
  }
`;

const DropdownList = styled.ul`
  width: 100%;
  max-height: 20rem;
  overflow-y: scroll;

  &::-webkit-scrollbar {
    display: none;
  }
`;
