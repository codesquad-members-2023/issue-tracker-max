import { styled } from 'styled-components';
import Icons from '../../design/Icons';
import Option from '../../constant/Option';
import DropdownPanelElement from '../../types/DropdownPanelElement';
import { useState } from 'react';
import DropdownType from '../../constant/DropdownType';
import Label from '../label/Label';

export default function DropdownPanel<T extends DropdownType>({
  label,
  baseElements,
}: {
  type: T;
  label: string;
  baseElements: DropdownPanelElement<T>[];
}) {
  const [elements, setElements] =
    useState<DropdownPanelElement<T>[]>(baseElements);

  const itemCheckHandler = (e: React.ChangeEvent, index: number) => {
    const input = e.target as HTMLInputElement;
    const form = input.closest('form');
    const formObject = formDataToObject(new FormData(form!));
    setElements((elements) =>
      elements.map((element, _index) => {
        if (_index === index) {
          element.option = !input.checked ? Option.Available : Option.Selected;
        }
        return element;
      })
    );
    console.log(formObject);
  };
  return (
    <Container>
      <Header>{label}</Header>
      <DropdownElements>
        {elements.map((element, index) => {
          const {
            text,
            option,
            imageUrl,
            textColor,
            backgroundColor,
            name,
          }: {
            text: string;
            option: Option;
            imageUrl?: string;
            textColor?: string;
            backgroundColor?: string;
            name?: string;
          } = element;

          return (
            <li key={text}>
              <Element htmlFor={text} $option={option}>
                {textColor ? (
                  <Label
                    {...{
                      textColor: textColor!,
                      backgroundColor: backgroundColor!,
                      name: name!,
                    }}
                  />
                ) : (
                  imageUrl && <img src={imageUrl} alt={text} />
                )}
                <Text>{text}</Text>

                <input
                  type="checkbox"
                  id={text}
                  name={textToName(text)}
                  onChange={(e) => itemCheckHandler(e, index)}
                />
                {option === Option.Selected ? (
                  <Icons.checkOnCircle />
                ) : (
                  <Icons.checkOffCircle />
                )}
              </Element>
            </li>
          );
        })}
      </DropdownElements>
    </Container>
  );
}

function textToName(text: string) {
  switch (text) {
    case '열린 이슈':
      return 'status[open]';
    case '내가 작성한 이슈':
      return 'status[author]';
    case '나에게 할당된 이슈':
      return 'status[assignee]';
    case '내가 댓글을 남긴 이슈':
      return 'status[commented]';
    case '닫힌 이슈':
      return 'status[closed]';
    default:
      return '';
  }
}

const Container = styled.article`
  width: 240px;
  border-radius: ${({ theme }) => theme.objectStyles.radius.large};
  border: 1px solid ${({ theme }) => theme.color.neutral.border.default};
  overflow: hidden;
`;

const Header = styled.h3`
  padding: 8px 16px;
  color: ${({ theme }) => theme.color.neutral.text.weak};
  border-bottom: 1px solid ${({ theme }) => theme.color.neutral.border.default};
  background-color: ${({ theme }) => theme.color.neutral.surface.default};
  ${({ theme }) => theme.font.display.medium[12]}
`;

const DropdownElements = styled.ul`
  & > li {
    background-color: ${({ theme }) => theme.color.neutral.surface.strong};
    padding: 8px 16px;
    display: flex;
    align-items: center;
    border-bottom: 1px solid
      ${({ theme }) => theme.color.neutral.border.default};
  }
  & > li:last-child {
    border: 0;
  }
`;

const Element = styled.label<{ $option: Option }>`
  width: 100%;
  display: flex;
  align-items: center;
  gap: 8px;
  ${({ theme, $option }) =>
    $option ? theme.font.selected.bold[16] : theme.font.available.medium[16]}
  color: ${({ theme, $option }) =>
    $option
      ? theme.color.neutral.text.strong
      : theme.color.neutral.text.default};

  & > input {
    display: none;
  }
`;

const Text = styled.span`
  width: 100%;
`;

function formDataToObject(formData: FormData) {
  const object: Record<string, (string | number)[]> = {};

  for (const [name] of formData.entries()) {
    const [key, value]: string[] = name.split(/\[|\]/).filter(Boolean);

    if (!object[key]) {
      object[key] = [];
    }
    object[key] = [...object[key], value];
  }

  return object;
}
